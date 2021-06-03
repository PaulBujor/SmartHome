package com.example.myhomeapplication.Local_Persistence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.EUser;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Models.Thresholds;
import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.Remote.Clients.DeviceClient;
import com.example.myhomeapplication.Remote.Clients.MeasurementClient;
import com.example.myhomeapplication.Remote.DeviceAPI;
import com.example.myhomeapplication.Remote.MeasurementAPI;
import com.example.myhomeapplication.Remote.ServiceGenerator;

import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Cache {

    private static Cache instance;
    private MeasurementClient measurementClient;
    private DeviceClient deviceClient;

    private final MutableLiveData<Measurement> latestTemperatureMeasurement;
    private final MutableLiveData<Measurement> latestHumidityMeasurement;
    private final MutableLiveData<Measurement> latestCO2Measurement;
    private final MutableLiveData<Measurement> latestSoundMeasurement;
    private final MutableLiveData<Thresholds> thresholds;
    private MutableLiveData<String> responseInformation;
    private MutableLiveData<Long> deviceID;


    private Cache() {
        latestTemperatureMeasurement = new MutableLiveData<>();
        latestHumidityMeasurement = new MutableLiveData<>();
        latestCO2Measurement = new MutableLiveData<>();
        latestSoundMeasurement = new MutableLiveData<>();
        thresholds = new MutableLiveData<>();
        responseInformation = new MutableLiveData<>();
        deviceID = new MutableLiveData<>();
        initClients();
        responseInformation = deviceClient.getResponseInformation();
    }

    public void initClients() {
        measurementClient = MeasurementClient.getInstance();
        deviceClient = DeviceClient.getInstance();
    }

    public static synchronized Cache getInstance() {
        if (instance == null)
            instance = new Cache();
        return instance;
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement() {
        return latestTemperatureMeasurement;
    }

    public LiveData<Measurement> getLatestHumidityMeasurement() {
        return latestHumidityMeasurement;
    }

    public LiveData<Measurement> getLatestCO2Measurement() {
        return latestCO2Measurement;
    }

    public LiveData<Measurement> getLatestSoundMeasurement() {
        return latestSoundMeasurement;
    }

    public MutableLiveData<List<Device>> getDevices() {
        return deviceClient.getDevices();
    }

    public MutableLiveData<Thresholds> getThresholds() {
        return thresholds;
    }

    public LiveData<List<Measurement>> getAllMeasurements(long deviceID, String measurementType) {
        return measurementClient.getAllMeasurements(deviceID, measurementType);
    }

    public void receiveLatestMeasurement(long deviceID, String measurementType) {

        switch (measurementType) {
            case MeasurementTypes.TYPE_TEMPERATURE:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestTemperatureMeasurement);
                break;
            case MeasurementTypes.TYPE_HUMIDITY:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestHumidityMeasurement);
                break;
            case MeasurementTypes.TYPE_CO2:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestCO2Measurement);
                break;
            case MeasurementTypes.TYPE_SOUND:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestSoundMeasurement);
                break;
            default:
                Log.wtf("Repository", "Wrong measurement type");
        }
    }

    public void getAllDevices(long userID) {
        deviceClient.getAllDevices(userID);
    }

    public void getThresholdsByDevice(long deviceID) {
        deviceClient.getThresholdsByDevice(deviceID).observeForever(thresholds::setValue);
    }

    public void addDevice(Device tmpDevice) {
        deviceClient.addDevice(tmpDevice);
    }

    public void updateThresholds(long deviceId, Thresholds thresholds) {
        deviceClient.updateThresholds(deviceId, thresholds);
    }

    public void deleteDevice(long deviceID) {
        deviceClient.deleteDevice(deviceID);
    }

    public MutableLiveData<String> getResponseInformation() {
        return responseInformation;
    }

    public void setResponseInformation(String s) {
        responseInformation.setValue(s);
    }

    public MutableLiveData<Long> getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID.setValue(deviceID);

    }
}
