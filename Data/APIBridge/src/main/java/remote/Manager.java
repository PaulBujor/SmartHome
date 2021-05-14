package remote;

import model.MeasurementSet;
import model.Settings;
import remote.api.MeasurementController;
import remote.api.SettingsController;

import java.io.IOException;

public class Manager {
    private MeasurementController measurementController;
    private SettingsController settingsController;

    public Manager() {
        measurementController = new MeasurementController();
        settingsController = new SettingsController();
    }

    public void addMeasurement(long deviceId, MeasurementSet measurementSet) throws IOException {
        measurementController.addMeasurement(deviceId,measurementSet);
    }

    public Settings getSettings(long deviceId) throws IOException {
        return settingsController.getDeviceSettings(deviceId);
    }
}
