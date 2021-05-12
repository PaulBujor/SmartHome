import config.IoT_Config;
import model.Measurement;
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
        Measurement temperature = new Measurement();
        Measurement co2 = new Measurement();
        Measurement humidity = new Measurement();
        Measurement alarm = new Measurement();

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
        temperature.setTimestamp(date);
        co2.setTimestamp(date);
        humidity.setTimestamp(date);
        alarm.setTimestamp(date);

        temperature.setValue(floatTemperature);
        co2.setValue(floatCO2);
        humidity.setValue(floatHumidity);
        alarm.setValue(floatAlarm);


        //i hate this
        try {
            controllers.addTemperature(deviceId, temperature);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            controllers.addCO2(deviceId, co2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            controllers.addHumidity(deviceId, humidity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (alarm.getValue() > 0) {
            try {
                controllers.addAlarm(deviceId, alarm);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
