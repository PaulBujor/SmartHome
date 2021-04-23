import config.IoT_Config;
import model.Measurement;
import remote.Manager;
import remote.api.MeasurementController;
import remote.api.SettingsController;

import java.io.IOException;

public class Bridge {
    private WebsocketClient client;
    private Manager controllers;

    public static void main(String[] args) {
        Bridge bridge = new Bridge(IoT_Config.getWsURI());
    }

    public Bridge(String wsURI) {
        client = new WebsocketClient(wsURI, this);
        controllers = new Manager(new MeasurementController(), new SettingsController());
    }

    public void addData(String data) {
        //todo see what data is received from lora and deserialize it into measurements
        long deviceId = 0;
        Measurement temperature = new Measurement();
        Measurement co2 = new Measurement();
        Measurement humidity = new Measurement();
        Measurement alarm = new Measurement();

        //some stuff goes here

        try {
            controllers.addTemperature(deviceId, temperature);
            controllers.addTemperature(deviceId, co2);
            controllers.addTemperature(deviceId, humidity);
            controllers.addTemperature(deviceId, alarm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
