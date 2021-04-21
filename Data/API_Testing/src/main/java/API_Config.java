public class API_Config {
    private static String localURI = "https://localhost:5001/api/";
    private static String azureURI = "https://sep4.azurewebsites.net/api/";
    private static boolean usingAzure = false; //local doesn't work because of lack of ssl certificate

    public static String getURI() {
        if(usingAzure)
            return azureURI;
        else
            return localURI;
    }

    public static boolean isUsingAzure() {
        return usingAzure;
    }
}
