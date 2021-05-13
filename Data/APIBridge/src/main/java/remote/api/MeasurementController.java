package remote.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import config.API_Config;
import model.Measurement;
import model.MeasurementSet;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeasurementController {
    private String measurementType;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private CloseableHttpClient client;
    private Type listType = new TypeToken<List<Measurement>>() {
    }.getType();

    private Object threadLock = new Object();

    /**
     * This method creates a new HTTP client to access the REST API.
     * Normally, after a number (~2) of requests per controller, the client dies and all the requests will timeout.
     * While this can be fixed (https://stackoverflow.com/questions/56942445/what-is-the-difference-beetween-max-connections-per-route-and-max-connections-to),
     * it still has to be handled.
     * 
     * Because of this, this method is also used to rebuild the client if a request times out,
     * after which the request is executed again.
     * If the second request fails, then it throws the timeout exception to the controller Manager.
     *
     * The method is as thread safe as possible, even though no multithreading is present in here o(*￣▽￣*)o
     *
     * 2 client builders are used, one for when the Azure API is used, and one for when a local running API is used.
     * This is changed by the static usingAzure variable in API_Config class.
     * It has to be different because local running server doesn't have certificates.
     */
    private void createClient() {
        int timeout = 10; //seconds
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000)
                .build();
        synchronized (threadLock) {
            if (client != null) {
                synchronized (threadLock) {
                    if (client != null) {
                        try {
                            System.out.println("Closing current client...");
                            client.close();
                            client = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if (client == null) {
                synchronized (threadLock) {
                    if (client == null) {
                        System.out.println("Creating new client...");
                        if (API_Config.isUsingAzure())
                            client = HttpClients.custom()
                                    .setDefaultRequestConfig(config)
                                    .setMaxConnPerRoute(5)
                                    .setMaxConnTotal(20)
                                    .build();
                        else {
                            try {
                                client = HttpClients.custom()
                                        .setDefaultRequestConfig(config)
                                        .setMaxConnPerRoute(5)
                                        .setMaxConnTotal(20)
                                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                                        .setSSLContext(new SSLContextBuilder()
                                                .loadTrustMaterial(null, new TrustStrategy() {
                                                    public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                                                        return true;
                                                    }
                                                }).build())
                                        .build();
                            } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * API Controller for generic measurements
     * <p>
     * used methods must contain which measurement type it is in string
     */
    public MeasurementController() {
        createClient();
    }


    public synchronized void addMeasurement(long deviceId, MeasurementSet measurement) throws IOException {
        StringEntity entity = new StringEntity(
                gson.toJson(measurement),
                ContentType.APPLICATION_JSON
        );

        System.out.println("Sending:\n" + gson.toJson(measurement));

        var post = new HttpPost(API_Config.getURI() + "devices/" + deviceId + "/measurements");
        post.setEntity(entity);

        CloseableHttpResponse response;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            System.out.println("Restarting HTTP client... Time:" + LocalDateTime.now());
            createClient();
            response = client.execute(post);
        }
    }

    public ArrayList<Measurement> getMeasurements(long deviceId, String measurementType) throws IOException {
        HttpGet get = new HttpGet(API_Config.getURI() + "devices/" + deviceId + "/measurements/" + measurementType
                + ((measurementType.equals("temperature") || measurementType.equals("sound")) ? "s" : ""));

        CloseableHttpResponse httpResponse = client.execute(get);
        ArrayList<Measurement> response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
        return response;
    }

    public Measurement getLatestMeasurement(long deviceId, String measurementType) throws IOException {
        HttpGet get = new HttpGet(API_Config.getURI() + "devices/" + deviceId + "/measurements/last-" + measurementType);

        CloseableHttpResponse httpResponse = client.execute(get);
        Measurement response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), Measurement.class);
        return response;
    }
}
