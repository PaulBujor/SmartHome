package com.example.myhomeapplication.Remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Remote.Response.MeasurementResponse;

import java.util.List;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LocalRepository {
    private static LocalRepository instance;
    private final MutableLiveData<Measurement> latestMeasurement;


    public LocalRepository() {
        this.latestMeasurement = new MutableLiveData<>();
    }

    public static synchronized LocalRepository getInstance()
    {
        if (instance == null)
            instance = new LocalRepository();
        return instance;
    }

    public LiveData<Measurement> getLatestMeasurement()
    {
        return latestMeasurement;
    }

    public void receiveLatestMeasurement()
    {
        TemperatureAPI temperatureAPI = TemperatureServiceGenerator.getTemperatureAPI();
        Call<MeasurementResponse> call = temperatureAPI.getLatestTemperatureMeasurement(1);
        call.enqueue(new Callback<MeasurementResponse>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<MeasurementResponse> call, Response<MeasurementResponse> response) {
                if (response.isSuccessful()){
                    latestMeasurement.setValue(response.body().getMeasurement());
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<MeasurementResponse> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });
    }

}
