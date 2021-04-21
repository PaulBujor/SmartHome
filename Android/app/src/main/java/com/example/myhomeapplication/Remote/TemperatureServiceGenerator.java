package com.example.myhomeapplication.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TemperatureServiceGenerator {

    private static final String BASE_URL = "https://sep4.azurewebsites.net/";
    private static MeasurementAPI temperatureAPI;

    public static MeasurementAPI getTemperatureAPI()
    {
        if (temperatureAPI == null)
        {
            temperatureAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
                    .create(MeasurementAPI.class);
        }
        return temperatureAPI;
    }

}
