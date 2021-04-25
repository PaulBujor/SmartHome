package remote;

import model.Measurement;
import model.Settings;
import remote.api.MeasurementController;
import remote.api.SettingsController;

import java.io.IOException;

public class Manager {
    private MeasurementController temperatureController;
    private MeasurementController humidityController;
    private MeasurementController co2Controller;
    private MeasurementController alarmController;
    private SettingsController settingsController;

    public Manager() {
        temperatureController = new MeasurementController("temperature");
        humidityController = new MeasurementController("humidity");
        co2Controller = new MeasurementController("co2");
        alarmController = new MeasurementController("alarm");
        settingsController = new SettingsController();
    }

    public void addTemperature(long deviceId, Measurement measurement) throws IOException {
        temperatureController.addMeasurement(deviceId, measurement);
    }

    public void addHumidity(long deviceId, Measurement measurement) throws IOException {
        humidityController.addMeasurement(deviceId, measurement);
    }

    public void addCO2(long deviceId, Measurement measurement) throws IOException {
        co2Controller.addMeasurement(deviceId, measurement);
    }

    public void addAlarm(long deviceId, Measurement measurement) throws IOException {
        alarmController.addMeasurement(deviceId, measurement);
    }

    public Settings getSettings(long deviceId) {
        return null;
    }
}
