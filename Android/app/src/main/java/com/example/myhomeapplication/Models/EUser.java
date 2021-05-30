package com.example.myhomeapplication.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EUser extends User {

    @JsonProperty("newDeviceName")
    private String newDeviceName;



    public EUser(String newDeviceName, long userId, String email, String password, List<Device> devices) {
        super(userId,email,password,devices);
        this.newDeviceName = newDeviceName;
    }

    public String getNewDeviceName() {
        return newDeviceName;
    }

    public void setNewDeviceName(String newDeviceName) {
        this.newDeviceName = newDeviceName;
    }
}
