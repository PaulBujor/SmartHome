package com.example.myhomeapplication.Authorization;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.Remote.ServiceGenerator;
import com.example.myhomeapplication.Remote.UserAPI;
import com.example.myhomeapplication.View.MainActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserManager {
    private MutableLiveData<User> user;

    private SharedPreferences sharedPreferences;
    private UserAPI userAPI;

    private static UserManager instance;
    private static Object threadLock = new Object();

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (threadLock) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    private UserManager() {
        user = new MutableLiveData<>();
        user.setValue(null);

        sharedPreferences = MainActivity.sharedPreferences;
        userAPI = ServiceGenerator.getUserAPI();

        String email = sharedPreferences.getString("sh-email", "");
        String password = sharedPreferences.getString("sh-password", "");

        if (!email.isEmpty() && !password.isEmpty()) {
            User user = new User(email, password);
            logIn(user);
        }
    }

    public User getUser() {
        return user.getValue();
    }

    public LiveData<User> getLiveUser() {
        return user;
    }

    private void setUser(User user) {
        this.user.setValue(user);
        sharedPreferences.edit().putString("sh-email", user.getEmail()).apply();
        sharedPreferences.edit().putString("sh-password", user.getPassword()).apply();
    }

    public void logIn(User user) {
        Call<User> call = userAPI.login(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200)
                    setUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void register(User user) {
        Call<ResponseBody> call = userAPI.register(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200)
                    setUser(user);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void logout() {
        user.setValue(null);
        sharedPreferences.edit().putString("sh-email", "").apply();
        sharedPreferences.edit().putString("sh-password", "").apply();
    }
}
