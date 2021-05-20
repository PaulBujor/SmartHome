package com.example.myhomeapplication.Local_Persistence;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DataRetrieverWorker extends Worker {

    private Cache repository;

    public DataRetrieverWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Cache.getInstance();
    }

    @NonNull
    @Override
    public Result doWork() {
        //TODO get current deviceID from the repository repository.getCurrentDeviceID();
        try {
            repository.receiveLatestMeasurement(420, MeasurementTypes.TYPE_TEMPERATURE);
            repository.receiveLatestMeasurement(420, MeasurementTypes.TYPE_HUMIDITY);
            repository.receiveLatestMeasurement(420, MeasurementTypes.TYPE_CO2);
            repository.receiveLatestMeasurement(420, MeasurementTypes.TYPE_ALARM);
        }
        catch (Exception e) {
            Log.d("DATA_RETRIEVER_WORKER", e.getMessage());
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }
}
