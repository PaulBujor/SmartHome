package model;

public class Settings {
    private long settingsId;
    private Configuration deviceConfiguration;
    private Configuration alarmConfiguration;
    private Configuration temperatureConfiguration;
    private Configuration Co2Configuration;
    private Configuration humidityConfiguration;

    public Settings() {
    }

    public Settings(long settingsId, Configuration deviceConfiguration, Configuration alarmConfiguration, Configuration temperatureConfiguration, Configuration co2Configuration, Configuration humidityConfiguration) {
        this.settingsId = settingsId;
        this.deviceConfiguration = deviceConfiguration;
        this.alarmConfiguration = alarmConfiguration;
        this.temperatureConfiguration = temperatureConfiguration;
        Co2Configuration = co2Configuration;
        this.humidityConfiguration = humidityConfiguration;
    }

    public long getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(long settingsId) {
        this.settingsId = settingsId;
    }

    public Configuration getDeviceConfiguration() {
        return deviceConfiguration;
    }

    public void setDeviceConfiguration(Configuration deviceConfiguration) {
        this.deviceConfiguration = deviceConfiguration;
    }

    public Configuration getAlarmConfiguration() {
        return alarmConfiguration;
    }

    public void setAlarmConfiguration(Configuration alarmConfiguration) {
        this.alarmConfiguration = alarmConfiguration;
    }

    public Configuration getTemperatureConfiguration() {
        return temperatureConfiguration;
    }

    public void setTemperatureConfiguration(Configuration temperatureConfiguration) {
        this.temperatureConfiguration = temperatureConfiguration;
    }

    public Configuration getCo2Configuration() {
        return Co2Configuration;
    }

    public void setCo2Configuration(Configuration co2Configuration) {
        Co2Configuration = co2Configuration;
    }

    public Configuration getHumidityConfiguration() {
        return humidityConfiguration;
    }

    public void setHumidityConfiguration(Configuration humidityConfiguration) {
        this.humidityConfiguration = humidityConfiguration;
    }
}
