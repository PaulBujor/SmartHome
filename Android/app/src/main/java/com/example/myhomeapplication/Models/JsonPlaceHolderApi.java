package com.example.myhomeapplication.Models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("api/temperatures/{id}")
    Call<List<Measurement>> getMeasurements(); //I guess here we will need an id
}
