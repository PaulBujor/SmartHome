package config;

public class IoT_Config {
    private static String wsURI = "wss://iotnet.cibicom.dk/app?token=vnoTvQAAABFpb3RuZXQuY2liaWNvbS5ka3snqGf3zNbLFCZtJMK8KLs=";
    private static String httpServer = "https://iotnet.teracom.dk/apps/websocket.html?token=vnoTvQAAABFpb3RuZXQuY2liaWNvbS5ka3snqGf3zNbLFCZtJMK8KLs=";
    private static String appKey = "635966D4505560A6877C9547BA8DF772";
    private static String appEUI = "611AAD47B9E2ED1C";
    private static String euiDev1 = "0004A30B00251192";

    public static String getWsURI() {
        return wsURI;
    }
}
