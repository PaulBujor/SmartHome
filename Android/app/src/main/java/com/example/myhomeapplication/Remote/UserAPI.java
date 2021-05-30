package com.example.myhomeapplication.Remote;

import com.example.myhomeapplication.Models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("api/users/login")
    Call<User> login(@Body User user);

    @POST("api/users/register")
    Call<ResponseBody> register(@Body User user);
}
