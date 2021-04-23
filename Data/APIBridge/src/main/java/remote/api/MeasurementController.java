package remote.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import config.API_Config;
import model.Measurement;
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
import java.util.ArrayList;
import java.util.List;

public class MeasurementController {
    private String measurementType;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
    private CloseableHttpClient client;
    private Type listType = new TypeToken<List<Measurement>>() {
    }.getType();

    /**
     * API Controller for generic measurements
     *
     * used methods must contain which measurement type it is in string
     */
    public MeasurementController() {
        if (API_Config.isUsingAzure())
            client = HttpClients.createDefault();
        else {
            try {
                client = HttpClients.custom().
                        setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).
                        setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                                return true;
                            }
                        }).build()).build();
            } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * API Controller for specific type measurements
     *
     * @param measurementType represents which mreasurement this controller is for. eg. "temperature" or "co2"
     */
    public MeasurementController(String measurementType) {
        this();
        this.measurementType = measurementType;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public void addMeasurement(long deviceId, Measurement measurement, String measurementType) throws IOException {
                StringEntity entity = new StringEntity(
                gson.toJson(measurement),
                ContentType.APPLICATION_JSON
        );

        var post = new HttpPost(API_Config.getURI() + "/devices/" + deviceId + "/" + measurementType
                + ((measurementType.equals("temperature") || measurementType.equals("alarm")) ? "s" : ""));
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
    }

    public ArrayList<Measurement> getMeasurements(long deviceId, String measurementType) throws IOException, InterruptedException {
        HttpGet get = new HttpGet(API_Config.getURI() + "/devices/" + deviceId + "/" + measurementType
                + ((measurementType.equals("temperature") || measurementType.equals("alarm")) ? "s" : ""));

        CloseableHttpResponse httpResponse = client.execute(get);
        ArrayList<Measurement> response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
        return response;
    }

    public Measurement getLatestMeassurement(long deviceId, String measurementType) throws IOException, InterruptedException {
        HttpGet get = new HttpGet(API_Config.getURI() + "/devices/" + deviceId + "/last-" + measurementType);

        CloseableHttpResponse httpResponse = client.execute(get);
        Measurement response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), Measurement.class);
        return response;
    }



    public void addMeasurement(long deviceId, Measurement measurement) throws IOException {
        if(measurementType == null || measurementType.isEmpty())
            throw new IllegalArgumentException("This Controller doesn't have a specific measurementType");

        StringEntity entity = new StringEntity(
                gson.toJson(measurement),
                ContentType.APPLICATION_JSON
        );

        var post = new HttpPost(API_Config.getURI() + "/devices/" + deviceId + "/" + measurementType
                + ((measurementType.equals("temperature") || measurementType.equals("alarm")) ? "s" : ""));
        post.setEntity(entity);

        CloseableHttpResponse response = client.execute(post);
    }

    public ArrayList<Measurement> getMeasurements(long deviceId) throws IOException, InterruptedException {
        if(measurementType == null || measurementType.isEmpty())
            throw new IllegalArgumentException("This Controller doesn't have a specific measurementType");

        HttpGet get = new HttpGet(API_Config.getURI() + "/devices/" + deviceId + "/" + measurementType
                + ((measurementType.equals("temperature") || measurementType.equals("alarm")) ? "s" : ""));

        CloseableHttpResponse httpResponse = client.execute(get);
        ArrayList<Measurement> response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), listType);
        return response;
    }

    public Measurement getLatestMeassurement(long deviceId) throws IOException, InterruptedException {
        if(measurementType == null || measurementType.isEmpty())
            throw new IllegalArgumentException("This Controller doesn't have a specific measurementType");

        HttpGet get = new HttpGet(API_Config.getURI() + "/devices/" + deviceId + "/last-" + measurementType);

        CloseableHttpResponse httpResponse = client.execute(get);
        Measurement response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), Measurement.class);
        return response;
    }
}
