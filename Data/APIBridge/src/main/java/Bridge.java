import config.IoT_Config;
import model.MeasurementSet;
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
        int intAlarm = Integer.parseInt(hexMeasurement[4], 16);

        float floatHumidity = ((float) intHumidity);
        float floatTemperature = ((float) intTemperature);
        float floatCO2 = ((float) intCO2);
        float floatNoise = ((float) intNoise);
        float floatAlarm = ((float) intAlarm);

        System.out.println("\n== == == == ==");
        System.out.println("Temperature: " + floatTemperature + "\nHumidity: " + floatHumidity + "\nCO2: " + floatCO2 + "\nSound " + floatNoise + "\nAlarm " + floatAlarm + "\n");
        MeasurementSet measurement = new MeasurementSet(date, floatTemperature, floatCO2,floatHumidity, floatAlarm);


        //i hate this
        try {
            controllers.addMeasurement(deviceId, measurement);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
