package com.example.myhomeapplication.Remote;

import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.EUser;
import com.example.myhomeapplication.Models.Thresholds;
import com.example.myhomeapplication.Models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DeviceAPI {


    @GET("api/users/{id}/devices")
    Call<List<Device>> getAllDevices(@Path("id") long userID);

    @GET("api/devices/{id}/thresholds")
    Call<Thresholds> getThresholdsByDevice(@Path("id") long deviceID);

    @POST("api/users/{id}/devices/{deviceId}")
    Call<ResponseBody> addDevice(@Path("id") long userID, @Path("deviceId") long deviceID, @Body EUser eUser);

    @PATCH("api/devices/{id}/thresholds")
    Call<ResponseBody> updateThresholds(@Path("id") long deviceID, @Body Thresholds thresholds);

   /* @DELETE("api/users/{id}/devices/{deviceId}")*/
    @HTTP(method= "DELETE", path = "api/users/{id}/devices/{deviceId}", hasBody = true)
    Call<ResponseBody> deleteDevice(@Path("id") long userID,@Path("deviceId") long deviceID, @Body User user);
    //TODO implement
    /*
    @DELETE("api/devices/{id}/thresholds")

    @PATCH("api/devices/{id}/name")*/
}
