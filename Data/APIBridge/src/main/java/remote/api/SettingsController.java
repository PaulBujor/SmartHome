package remote.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.API_Config;
import model.Settings;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SettingsController {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
    private CloseableHttpClient client;

    private Object threadLock = new Object();

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

    public SettingsController() {
        createClient();
    }

    public Settings getDeviceSettings(long deviceId) throws IOException {
        HttpGet get = new HttpGet(API_Config.getURI() + "devices/" + deviceId + "/settings");

        CloseableHttpResponse httpResponse = client.execute(get);
        Settings response = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), Settings.class);
        return response;
    }
}
