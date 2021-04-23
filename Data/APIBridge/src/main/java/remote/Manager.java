package remote;

import model.Measurement;
import model.Settings;
import remote.api.MeasurementController;
import remote.api.SettingsController;

import java.io.IOException;

public class Manager {
    private MeasurementController measurementController;
    private SettingsController settingsController;

    public Manager(MeasurementController measurementController, SettingsController settingsController) {
        this.measurementController = measurementController;
        this.settingsController = settingsController;
    }

    public void addTemperature(long deviceId, Measurement measurement) throws IOException {
        measurementController.addMeasurement(deviceId, measurement, "temperature");
    }

    public void addHumidity(long deviceId, Measurement measurement) throws IOException {
        measurementController.addMeasurement(deviceId, measurement, "humidity");
    }

    public void addCO2(long deviceId, Measurement measurement) throws IOException {
        measurementController.addMeasurement(deviceId, measurement, "co2");
    }

    public void addAlarm(long deviceId, Measurement measurement) throws IOException {
        measurementController.addMeasurement(deviceId, measurement, "alarm");
    }

    public Settings getSettings(long deviceId) {
        return null;
    }
}
