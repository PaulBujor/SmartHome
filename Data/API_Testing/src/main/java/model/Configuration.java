package model;

public class Configuration {
    private long configurationId;
    private boolean active;
    private double minOrDefault;
    private double max;

    public Configuration() {
    }

    public Configuration(long configurationId, boolean active, double minOrDefault, double max) {
        this.configurationId = configurationId;
        this.active = active;
        this.minOrDefault = minOrDefault;
        this.max = max;
    }

    public long getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(long configurationId) {
        this.configurationId = configurationId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getMinOrDefault() {
        return minOrDefault;
    }

    public void setMinOrDefault(double minOrDefault) {
        this.minOrDefault = minOrDefault;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
