package com.example.myhomeapplication.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myhomeapplication.Local_Persistence.Cache;
import com.example.myhomeapplication.Models.Measurement;

import java.util.List;

public class HumidityViewModel extends ViewModel {
    private Cache repository;
    public HumidityViewModel(){
        repository = Cache.getInstance();
    }

    public LiveData<List<Measurement>> getAlMeasurements(int deviceID,String measurementType){
        return repository.getAllMeasurements(deviceID,measurementType);
    }

    public LiveData<Measurement> getLastMeasurement(){
        return repository.getLatestHumidityMeasurement();
    }
}
