package com.example.myhomeapplication.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class DeviceServiceGenerator {

    private static final String BASE_URL  = "https://sep4.azurewebsites.net/";
    private static DeviceAPI deviceAPI;

    public static DeviceAPI getDeviceAPI(){
        if(deviceAPI==null){
            deviceAPI= new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
                    .create(DeviceAPI.class);
        }
        return deviceAPI;
    }
}
