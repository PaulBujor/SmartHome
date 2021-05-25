package com.example.myhomeapplication.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Thresholds {

    @JsonProperty("thresholdsId")
    private long thresholdsID;

    @JsonProperty("minHumidity")
    private int minHumidity;

    @JsonProperty("maxHumidity")
    private int maxHumidity;

    @JsonProperty("minTemperature")
    private int minTemperature;

    @JsonProperty("maxTemperature")
    private int maxTemperature;

    @JsonProperty("minCo2")
    private int minCO2;

    @JsonProperty("maxCo2")
    private int maxCO2;

    public Thresholds() {

    }

    public Thresholds(long thresholdsID, int minHumidity, int maxHumidity, int minTemperature, int maxTemperature, int minCO2, int maxCO2) {
        this.thresholdsID = thresholdsID;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minCO2 = minCO2;
        this.maxCO2 = maxCO2;
    }

    public long getThresholdsID() {
        return thresholdsID;
    }

    public void setThresholdsID(long thresholdsID) {
        this.thresholdsID = thresholdsID;
    }

    public int getMinHumidity() {
        return minHumidity;
    }

    public void setMinHumidity(int minHumidity) {
        this.minHumidity = minHumidity;
    }

    public int getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(int maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getMinCO2() {
        return minCO2;
    }

    public void setMinCO2(int minCO2) {
        this.minCO2 = minCO2;
    }

    public int getMaxCO2() {
        return maxCO2;
    }

    public void setMaxCO2(int maxCO2) {
        this.maxCO2 = maxCO2;
    }
}
