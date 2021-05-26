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
    private MutableLiveData<List<Device>> devicesMutable;
    private MutableLiveData<Thresholds> thresholdsMutable;

    public DeviceSettingsViewModel() {
        repository = Cache.getInstance();
        devices = new ArrayList<>();
        devicesMutable = new MutableLiveData<>();
        thresholdsMutable = new MutableLiveData<>();

        repository.getThresholds().observeForever(thresholds -> {
            thresholdsMutable.setValue(thresholds);
        });

        init();
    }

    public void getThresholds(String id) {

        repository.getThresholdsByDevice(Long.parseLong(id));
    }

    public LiveData<Thresholds> getThresholdsMutable(){
        return thresholdsMutable;
    }



    public void init() {
        devicesMutable = repository.getAllDevices(1);

    }

    public MutableLiveData<List<Device>> getDevicesMutable() {
        return devicesMutable;
    }

    public void setDevicesMutable(MutableLiveData<List<Device>> devicesMutable) {
        this.devicesMutable = devicesMutable;
    }

    public ArrayList<DeviceItem> getAllDevices() {
        ArrayList<DeviceItem> deviceItems = new ArrayList<>();
        devices.clear();
        List<Device> devices = devicesMutable.getValue();

        for (Device item : devices
        ) {
            deviceItems.add(new DeviceItem(String.valueOf(item.getDeviceID()), item.getDeviceName()));
        }
        return deviceItems;
    }

    public void updateThresholds() {

    }

    public void deleteDevice() {

    }
}
