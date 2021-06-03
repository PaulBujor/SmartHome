package com.example.myhomeapplication.Remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ServiceGenerator {
    private static final String BASE_URL = "https://sep4.azurewebsites.net/";
    private static MeasurementAPI measurementAPI;
    private static DeviceAPI deviceAPI;
    private static UserAPI userAPI;
    private static OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build();

    public static MeasurementAPI getMeasurementAPI() {
        if (measurementAPI == null) {
            measurementAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(MeasurementAPI.class);
        }
        return measurementAPI;
    }

    public static DeviceAPI getDeviceAPI() {
        if (deviceAPI == null) {
            deviceAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(DeviceAPI.class);
        }
        return deviceAPI;
    }

    public static UserAPI getUserAPI() {
        if (userAPI == null) {
            userAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(UserAPI.class);
        }
        return userAPI;
    }
}
