package com.example.myhomeapplication.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {

    @JsonProperty("deviceId")
    private long deviceID;

    @JsonProperty("deviceName")
    private String deviceName;

    public Device(){

    }

    public Device(long deviceID, String deviceName) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
    }

    public long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(long deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
