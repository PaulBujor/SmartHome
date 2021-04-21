package com.example.myhomeapplication.Local_Persistence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Remote.MeasurementAPI;
import com.example.myhomeapplication.Remote.TemperatureServiceGenerator;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Cache {
    private static Cache instance;
    private final MutableLiveData<Measurement> latestMeasurement;
    private  MutableLiveData<List<Measurement>> allMeasurements;


    public Cache() {

        this.latestMeasurement = new MutableLiveData<>();
        this.allMeasurements = new MutableLiveData<>();
    }

    public static synchronized Cache getInstance()
    {
        if (instance == null)
            instance = new Cache();
        return instance;
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement()
    {
        return latestMeasurement;
    }

    public LiveData<List<Measurement>> getAllTemperatureMeasurements()
    {
        return allMeasurements;
    }

    public void receiveLatestMeasurement(int deviceID, String measurementType)
    {
        MeasurementAPI temperatureAPI = TemperatureServiceGenerator.getTemperatureAPI();
        Call<Measurement> call = temperatureAPI.getLatestMeasurement(deviceID, measurementType);
        call.enqueue(new Callback<Measurement>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.code() == 200){
                    latestMeasurement.setValue(response.body());
                    Log.i("HTTPResponseCode", String.valueOf(response.code()));
                }
                else
                {
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code()));
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void receiveAllMeasurements() {

        MeasurementAPI temperatureAPI = TemperatureServiceGenerator.getTemperatureAPI();
        Call<Measurement> call = temperatureAPI.getMeasurements(1,"temperatures");
        call.enqueue(new Callback<Measurement>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.isSuccessful()){
                    latestMeasurement.setValue(response.body());
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
