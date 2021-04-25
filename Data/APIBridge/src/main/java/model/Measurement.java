package model;

import java.util.Date;
import java.util.Objects;

public class Measurement {
    private long measurementId;
    private Date timestamp;
    private double value;

    public Measurement() {
    }

    public Measurement(long measurementId, Date timestamp, double value) {
        this.measurementId = measurementId;
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(long measurementId) {
        this.measurementId = measurementId;
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
    public String toString() {
        return "Measurement{" +
                "measurementID=" + measurementId +
                ", timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return value == that.value &&
                timestamp.toString().equals(that.timestamp.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, value);
    }
}
