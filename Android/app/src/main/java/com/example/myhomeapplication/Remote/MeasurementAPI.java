package com.example.myhomeapplication.Remote;

import com.example.myhomeapplication.Models.Measurement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface MeasurementAPI {

    @GET("api/devices/{id}/measurements/last-{type}")
    Call<Measurement> getLatestMeasurement(@Path("id") long deviceID, @Path("type") String measurementType);

    @GET("api/devices/{id}/measurements/{type}")
    Call<List<Measurement>> getMeasurements(@Path("id") int deviceID, @Path("type") String measurementType);

    @GET("api/{type}/{id}")
    Call<Measurement> getMeasurementsByID(@Path("id") int measurementID, @Path("type") String measurementType);



}
