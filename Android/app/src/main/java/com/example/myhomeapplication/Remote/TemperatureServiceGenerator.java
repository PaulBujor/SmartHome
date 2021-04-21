package com.example.myhomeapplication.Remote;

import com.example.myhomeapplication.Models.Measurement;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TemperatureServiceGenerator {

    private static final String BASE_URL = "https://sep4.azurewebsites.net/";
    private static TemperatureAPI temperatureAPI;

    public static TemperatureAPI getTemperatureAPI()
    {
        if (temperatureAPI == null)
        {
            temperatureAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
                    .create(TemperatureAPI.class);
        }
        return temperatureAPI;
    }

}
