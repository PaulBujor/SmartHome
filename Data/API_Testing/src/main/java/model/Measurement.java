package model;

import java.util.Date;

public class Measurement {
    private long measurementID;
    private Date timestamp;
    private double value;

    public Measurement() {
    }

    public Measurement(long measurementID, Date timestamp, double value) {
        this.measurementID = measurementID;
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getMeasurementID() {
        return measurementID;
    }

    public void setMeasurementID(long measurementID) {
        this.measurementID = measurementID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
