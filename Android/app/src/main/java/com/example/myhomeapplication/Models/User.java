package com.example.myhomeapplication.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class User {

    @JsonProperty("userId")
    private long userID;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String Password;

    private List<Device> devices;

 /*   public User(){

    }*/



    public User(long userID, String email, String password, List<Device> devices) {
        this.userID = userID;
        this.email = email;
        Password = password;
        this.devices = devices;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
