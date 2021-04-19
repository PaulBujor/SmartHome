package com.example.myhomeapplication.Remote;

import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Remote.Response.MeasurementResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface TemperatureAPI {

    @GET("api/devices/{id}/last_temperature")
    Call<MeasurementResponse> getLatestTemperatureMeasurement(@Path("id") int id);
}
