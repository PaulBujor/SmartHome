package com.example.myhomeapplication.ViewModel;

import android.icu.util.Measure;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Remote.LocalRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TemperatureViewModel extends ViewModel {

    private MutableLiveData<List<Measurement>> temperatureMeasurements;
    private LocalRepository repository;

    private int i = 1;

    public TemperatureViewModel() {
        temperatureMeasurements = new MutableLiveData<>();
        List<Measurement> newList = new ArrayList<>();
        newList.add(new Measurement(0, new Date(), 19));
        temperatureMeasurements.setValue(newList);

        repository = LocalRepository.getInstance();
    }

    public LiveData<List<Measurement>> getAllTemperatureMeasurements()
    {
        return temperatureMeasurements;
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement()
    {
        return repository.getLatestMeasurement();
    }

    public void receiveLatestTemperatureMeasurement()
    {
        repository.receiveLatestMeasurement();
    }




    public void addDataSimulation() throws InterruptedException {

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
    }

}
