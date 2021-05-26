package com.example.myhomeapplication.Remote;

import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.EUser;
import com.example.myhomeapplication.Models.Thresholds;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeviceAPI {


    @GET("api/users/{id}/devices")
    Call<List<Device>> getAllDevices(@Path("id") long userID);

    @GET("api/devices/{id}/thresholds")
    Call<Thresholds> getThresholdsByDevice(@Path("id") long deviceID);

    @POST("api/users/{id}/devices/{deviceId}")
    Call<EUser> addDevice(@Path("id") long userID, @Path("deviceId") long deviceID, @Body EUser eUser);

    @PATCH("api/devices/{id}/thresholds")
    Call<Thresholds> updateThresholds(@Path("id") long deviceID,@Body Thresholds thresholds);

    //TODO implement
    /*
    @DELETE("api/devices/{id}/thresholds")

    @PATCH("api/devices/{id}/name")*/
}
