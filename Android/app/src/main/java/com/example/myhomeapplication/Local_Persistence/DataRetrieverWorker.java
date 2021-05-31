package com.example.myhomeapplication.Local_Persistence;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.ViewModel.DeviceSettingsViewModel;

import java.util.List;

public class DataRetrieverWorker extends Worker {

    private Cache repository;


    public DataRetrieverWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        repository = Cache.getInstance();
    }

    @NonNull
    @Override
    public Result doWork() {
        long currentDeviceID=0;

        try {
            if(Cache.getInstance().getDeviceID().getValue()!=null){
                currentDeviceID = Cache.getInstance().getDeviceID().getValue();
            }
            else {
                User user = UserManager.getInstance().getUser();
                Device tmpDevice =  user.getDevices().get(0);
                currentDeviceID = tmpDevice.getDeviceID();
            }
            repository.receiveLatestMeasurement(currentDeviceID, MeasurementTypes.TYPE_TEMPERATURE);
            repository.receiveLatestMeasurement(currentDeviceID, MeasurementTypes.TYPE_HUMIDITY);
            repository.receiveLatestMeasurement(currentDeviceID, MeasurementTypes.TYPE_CO2);
            repository.receiveLatestMeasurement(currentDeviceID, MeasurementTypes.TYPE_SOUND);
        }
        catch (Exception e) {
            Log.d("DATA_RETRIEVER_WORKER", e.getMessage());
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }
}
