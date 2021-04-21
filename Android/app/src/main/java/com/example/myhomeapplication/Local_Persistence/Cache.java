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

    private final MutableLiveData<Measurement> latestTemperatureMeasurement;
    private final MutableLiveData<Measurement> latestHumidityMeasurement;
    private final MutableLiveData<Measurement> latestCO2Measurement;
    private final MutableLiveData<Measurement> latestAlarmMeasurement;
    private MutableLiveData<List<Measurement>> allMeasurements;


    public Cache() {

        this.latestTemperatureMeasurement = new MutableLiveData<>();
        this.latestHumidityMeasurement = new MutableLiveData<>();
        this.latestCO2Measurement = new MutableLiveData<>();
        this.latestAlarmMeasurement = new MutableLiveData<>();
        this.allMeasurements = new MutableLiveData<>();
    }

    public static synchronized Cache getInstance() {
        if (instance == null)
            instance = new Cache();
        return instance;
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement() {
        return latestTemperatureMeasurement;
    }

    public LiveData<Measurement> getLatestHumidityMeasurement() {
        return latestHumidityMeasurement;
    }

    public LiveData<Measurement> getLatestCO2Measurement() {
        return latestCO2Measurement;
    }

    public LiveData<Measurement> getLatestAlarmMeasurement() {
        return latestAlarmMeasurement;
    }


    public LiveData<List<Measurement>> getAllTemperatureMeasurements() {
        return allMeasurements;
    }

    public void receiveLatestMeasurement(int deviceID, String measurementType) {
        MeasurementAPI measurementAPI = TemperatureServiceGenerator.getMeasurementAPI();
        Call<Measurement> call = measurementAPI.getLatestMeasurement(deviceID, measurementType);
        Log.i("Cache", measurementType + " " + deviceID);
        call.enqueue(new Callback<Measurement>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.code() == 200) {
                    switch (measurementType) {
                        case MeasurementTypes.TYPE_TEMPERATURE: latestTemperatureMeasurement.setValue(response.body());
                        break;
                        case MeasurementTypes.TYPE_HUMIDITY: latestHumidityMeasurement.setValue(response.body());
                            break;
                        case MeasurementTypes.TYPE_CO2: latestCO2Measurement.setValue(response.body());
                            break;
                        case MeasurementTypes.TYPE_ALARM: latestAlarmMeasurement.setValue(response.body());
                            break;
                        default:
                            Log.wtf("Repository", "Wrong measurement type");
                    }
                    Log.i("HTTPResponseCode", String.valueOf(response.code()) + " " + measurementType);
                } else {
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code()  + " " + measurementType + "\n" + response.message()));
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

        MeasurementAPI temperatureAPI = TemperatureServiceGenerator.getMeasurementAPI();
        Call<Measurement> call = temperatureAPI.getMeasurements(1, "temperatures");
        call.enqueue(new Callback<Measurement>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.isSuccessful()) {
                    latestTemperatureMeasurement.setValue(response.body());
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
