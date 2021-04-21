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

    public LiveData<List<Measurement>> getAllTemperatureMeasurements()
    {
        return repository.getAllTemperatureMeasurements();
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement()
    {
        return repository.getLatestTemperatureMeasurement();
    }

    public void receiveLatestTemperatureMeasurement(int deviceID, String measurementType)
    {
        repository.receiveLatestMeasurement(deviceID, measurementType);
    }

    public void receiveAllTemperatureMeasurements()
    {
        repository.receiveAllMeasurements();
    }




/*    public void addDataSimulation() throws InterruptedException {

        new Thread(() -> {
        while (true)
        {
            List<Measurement> currentMeasurements;
            currentMeasurements = temperatureMeasurements.getValue();
            currentMeasurements.add(new Measurement(++i, new Date(), Math.random() * (35 - 15 + 1) + 15));
            temperatureMeasurements.postValue(currentMeasurements);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        }).start();
    }*/

}
