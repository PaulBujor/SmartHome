package com.example.myhomeapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myhomeapplication.Local_Persistence.Cache;
import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.DeviceItem;
import com.example.myhomeapplication.Models.Thresholds;

import java.util.ArrayList;
import java.util.List;

public class DeviceSettingsViewModel extends ViewModel {

    private Cache repository;
    private List<Device> devices;

    public DeviceSettingsViewModel(){
        repository = Cache.getInstance();
        devices = new ArrayList<>();
    }

    public Thresholds getThresholds(String id) {
        return null;
    }

    public ArrayList<DeviceItem> getAllDevices(){
       devices.clear();
       devices =  repository.getAllDevices(1);
       ArrayList<DeviceItem> deviceItems = new ArrayList<>();
        for (Device item: devices
             ) {
            deviceItems.add(new DeviceItem(item.getDeviceName(),String.valueOf(item.getDeviceID())));
        }
    return deviceItems;
    }

    public void updateThresholds(){

    }

    public void deleteDevice(){

    }
}
