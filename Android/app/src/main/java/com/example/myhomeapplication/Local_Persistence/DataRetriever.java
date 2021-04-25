package com.example.myhomeapplication.Local_Persistence;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class DataRetriever {

    private static DataRetriever instance;
    private Cache repository;
    private int delay = 5;


    public DataRetriever() {
        repository = Cache.getInstance();
    }

    public static synchronized DataRetriever getInstance() {
        if (instance == null)
            instance = new DataRetriever();
        return instance;
    }

    public void startRetrievingData(int deviceID) {
        new Thread(() -> {
            while (true) {
                repository.receiveLatestMeasurement(deviceID, MeasurementTypes.TYPE_TEMPERATURE);
                repository.receiveLatestMeasurement(deviceID, MeasurementTypes.TYPE_HUMIDITY);
                repository.receiveLatestMeasurement(deviceID, MeasurementTypes.TYPE_CO2);
                repository.receiveLatestMeasurement(deviceID, MeasurementTypes.TYPE_ALARM);
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException e) {
                    Log.wtf("DataRetriever", "Delay error");
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }
}
