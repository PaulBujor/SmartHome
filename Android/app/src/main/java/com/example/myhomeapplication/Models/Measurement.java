package com.example.myhomeapplication.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Measurement implements Comparable{
    @JsonProperty("measurementId")
    private long measurementID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = JsonFormat.DEFAULT_TIMEZONE)
    @JsonProperty("timestamp")
    private Date timestamp;
    @JsonProperty("value")
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

    @Override
    public int compareTo(Object o) {
        Measurement measurement = (Measurement) o;
        return timestamp.compareTo(measurement.getTimestamp());
    }
}
