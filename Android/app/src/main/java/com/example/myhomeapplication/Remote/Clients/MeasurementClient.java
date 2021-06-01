package com.example.myhomeapplication.Remote.Clients;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Local_Persistence.Cache;
import com.example.myhomeapplication.Local_Persistence.MeasurementTypes;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Remote.MeasurementAPI;
import com.example.myhomeapplication.Remote.ServiceGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MeasurementClient {

    private static MeasurementClient instance;


    public static synchronized MeasurementClient getInstance() {
        if (instance == null)
            instance = new MeasurementClient();
        return instance;
    }

    public LiveData<List<Measurement>> getAllMeasurements(int deviceID, String measurementType) {
        MutableLiveData<List<Measurement>> allMeasurements = new MutableLiveData<>();

        MeasurementAPI measurementAPI = ServiceGenerator.getMeasurementAPI();
        Call<List<Measurement>> call = measurementAPI.getMeasurements(deviceID, measurementType);
        call.enqueue(new Callback<List<Measurement>>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                if (response.code() == 200) {
                    List<Measurement> sortedMeasurements = response.body();
                    Collections.sort(sortedMeasurements);
                    allMeasurements.setValue(sortedMeasurements);
                    Log.i("HTTPResponseCode", String.valueOf(response.code()) + " " + measurementType);
                } else {
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code() + " " + measurementType + "\n" + response.message()));
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });

        return allMeasurements;
    }

    public void receiveLatestMeasurement(int deviceID, String measurementType, MutableLiveData<Measurement> aux) {

        MeasurementAPI measurementAPI = ServiceGenerator.getMeasurementAPI();
        Call<Measurement> call = measurementAPI.getLatestMeasurement(deviceID, measurementType);
        call.enqueue(new Callback<Measurement>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.code() == 200) {
                    aux.setValue(response.body());
                    Log.i("HTTPResponseCode", String.valueOf(response.code()) + " " + measurementType);
                } else {
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code() + " " + measurementType + "\n" + response.message()));
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
