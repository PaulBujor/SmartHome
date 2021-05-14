import config.IoT_Config;
import model.MeasurementSet;
import model.Settings;
import org.json.JSONException;
import org.json.JSONObject;
import remote.Manager;

import java.io.IOException;
import java.util.Date;

public class Bridge {
    private WebsocketClient client;
    private Manager controllers;

    public static void main(String[] args) {
        Bridge bridge = new Bridge(IoT_Config.getWsURI());
    }

    public Bridge(String wsURI) {
        client = new WebsocketClient(wsURI, this);
        controllers = new Manager();
        while (true) ;
    }

    public void addData(long deviceId, String data) {
        Date date = new Date(System.currentTimeMillis());

        String[] hexMeasurement = data.split("(?<=\\G....)");

        int intHumidity = Integer.parseInt(hexMeasurement[0], 16);
        int intTemperature = Integer.parseInt(hexMeasurement[1], 16);
        int intCO2 = Integer.parseInt(hexMeasurement[2], 16);
        int intNoise = Integer.parseInt(hexMeasurement[3], 16);

        float floatHumidity = ((float) intHumidity);
        float floatTemperature = ((float) intTemperature);
        float floatCO2 = ((float) intCO2);
        float floatNoise = ((float) intNoise);

        System.out.println("\n== == == == ==");
        System.out.println("Temperature: " + floatTemperature + "\nHumidity: " + floatHumidity + "\nCO2: " + floatCO2 + "\nSound " + floatNoise + "\n");
        MeasurementSet measurement = new MeasurementSet(date, floatTemperature, floatCO2, floatHumidity, floatNoise);

        try {
            if (intHumidity == 0 && intTemperature == 0 && intCO2 == 0 && intNoise == 0) {
                System.out.println("Empty data from " + deviceId);
            } else {
                controllers.addMeasurement(deviceId, measurement);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            updateDeviceSettings(deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateDeviceSettings(long deviceId) throws JSONException {
        try {
            Settings settings = controllers.getSettings(deviceId);

            String hexDeviceId = convertToHex(deviceId, 16);

            String deviceConfiguration = convertToHex(settings.isDeviceConfiguration() ? 1 : 0, 2);
            String minHumidity = convertToHex(settings.getMinHumidity(), 4);
            String maxHumidity = convertToHex(settings.getMaxHumidity(), 4);
            String minTemperature = convertToHex(settings.getMinTemperature(), 4);
            String maxTemperature = convertToHex(settings.getMaxTemperature(), 4);
            String minCo2 = convertToHex(settings.getMinCo2(), 4);
            String maxCo2 = convertToHex(settings.getMaxCo2(), 4);

            String payload = "".concat(minHumidity).concat(maxHumidity).concat(minTemperature).concat(maxTemperature).concat(minCo2).concat(maxCo2).concat(deviceConfiguration);

            String uplink = new JSONObject()
                    .put("cmd", "tx")
                    .put("EUI", hexDeviceId)
                    .put("port", 2)
                    .put("data", payload).toString();
            //0-3 min humidity
            //4-7 max humid
            //8-11 min temperature
            //12-15 max temperature
            //16-19 min co2
            //20-23 max co2
            //24-25 device on/off

            System.out.println("Sending " + uplink);
            client.sendDownLink(uplink);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertToHex(long sourceNum, int targetLength) {
        String source = Long.toHexString(sourceNum);
        while (source.length() < targetLength) {
            source = "0".concat(source);
        }
        return source;
    }

    private String convertToHex(int sourceNum, int targetLength) {
        return convertToHex((long) sourceNum, targetLength);
    }
}
