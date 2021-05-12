package model;

import java.util.Date;

public class MeasurementSet {
    private Date timestamp;
    private double temperature;
    private double co2;
    private double humidity;
    private double alarm;

    public MeasurementSet() {
    }

    public MeasurementSet(Date timestamp, double temperature, double co2, double humidity, double alarm) {
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.co2 = co2;
        this.humidity = humidity;
        this.alarm = alarm;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getAlarm() {
        return alarm;
    }

    public void setAlarm(double alarm) {
        this.alarm = alarm;
    }

    @Override
    public String toString() {
        return "MeasurementSet{" +
                "timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", co2=" + co2 +
                ", humidity=" + humidity +
                ", alarm=" + alarm +
                '}';
    }
}
