import com.google.gson.Gson;
import model.Measurement;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MeasurementController {
    private Gson gson = new Gson();
    private CloseableHttpClient client;

    public static void main(String[] args) {
        var dot = new MeasurementController();
        try {
            var response = dot.getMeasurements(1, "temperatures");
            System.out.println(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

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

  /*  public void addMeasurement(long deviceId, Measurement measurement, String measurementType) throws IOException {
        StringEntity entity = new StringEntity(
                gson.toJson(measurement),
                ContentType.APPLICATION_JSON
        );

        var post = new HttpPost(API_Config.getURI() + "/devices/" + deviceId + "/" + measurementType);
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
    }*/

    public String getMeasurements(long deviceId, String measurementType) throws IOException, InterruptedException {
        HttpGet get = new HttpGet(API_Config.getURI() + "/devices/" + deviceId + "/" + measurementType);

        CloseableHttpResponse httpResponse = client.execute(get);
        String response = EntityUtils.toString(httpResponse.getEntity());
        return response;

//        HttpGet request = HttpRequest.newBuilder()
//                .uri(URI.create(API_Config.getURI() + "devices/" + deviceId + "/" + measurementType))
//                .header("Content-Type", "application/json")
//                .GET()
//                .build();
//        CloseableHttpResponse response = client.execute(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
    }
}
