package com.example.myhomeapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Local_Persistence.Cache;

import java.util.List;

public class TemperatureViewModel extends ViewModel {

    private Cache repository;

    public TemperatureViewModel() {
        repository = Cache.getInstance();
    }

    public LiveData<List<Measurement>> getAllMeasurements(long deviceID, String measurementType)
    {
        return repository.getAllMeasurements(deviceID, measurementType);
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement()
    {
        return repository.getLatestTemperatureMeasurement();
    }

    public LiveData<Long> getDeviceID()
    {
        return repository.getDeviceID();
    }

}
