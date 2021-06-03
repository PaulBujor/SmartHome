package com.example.myhomeapplication.Remote.Clients;

import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Local_Persistence.Cache;
import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.EUser;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Models.Thresholds;
import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.Remote.DeviceAPI;
import com.example.myhomeapplication.Remote.ServiceGenerator;
import com.example.myhomeapplication.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DeviceClient {

    private static DeviceClient instance;
    private MutableLiveData<String> responseInformation;
    private MutableLiveData<List<Device>> devices;

    public DeviceClient() {
        responseInformation = new MutableLiveData<>();
        devices = new MutableLiveData<>();
    }

    public static synchronized DeviceClient getInstance() {
        if (instance == null)
            instance = new DeviceClient();
        return instance;
    }

    public void getAllDevices(long userID) {
        DeviceAPI deviceAPI = ServiceGenerator.getDeviceAPI();
        Call<List<Device>> call = deviceAPI.getAllDevices(userID);

        call.enqueue(new Callback<List<Device>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.code() == 200) {
                    devices.setValue(response.body());
                    Log.i("HTTP_Devices", String.valueOf(response.code()));
                } else
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code() + "\n" + response.message()));

            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();

            }
        });

    }

    public MutableLiveData<Thresholds>  getThresholdsByDevice(long deviceID) {

        MutableLiveData<Thresholds> aux = new MutableLiveData<>();

        DeviceAPI deviceAPI = ServiceGenerator.getDeviceAPI();
        Call<Thresholds> call = deviceAPI.getThresholdsByDevice(deviceID);
        call.enqueue(new Callback<Thresholds>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Thresholds> call, Response<Thresholds> response) {
                if (response.code() == 200) {
                    aux.setValue(response.body());
                    responseInformation.setValue("Thresholds displayed for device : " + deviceID);
                } else
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code() + "\n" + response.message()));
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Thresholds> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
                responseInformation.setValue("Encountered an error while getting thresholds.");
            }
        });
        return aux;
    }

    public void addDevice(Device tmpDevice) {
        DeviceAPI deviceAPI = ServiceGenerator.getDeviceAPI();

        User currentUser = UserManager.getInstance().getUser();
        EUser tmpEUser = new EUser(tmpDevice.getDeviceName(), currentUser.getUserID(), currentUser.getEmail(), currentUser.getPassword(), currentUser.getDevices());
        Call<ResponseBody> call = deviceAPI.addDevice(tmpEUser.getUserID(), tmpDevice.getDeviceID(), tmpEUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                responseInformation.setValue(response.message());
                if (response.code() == 200) {
                    responseInformation.setValue("Device added successfully.");
                    getAllDevices(currentUser.getUserID());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
                responseInformation.setValue("Encountered an error while adding a device.");
            }
        });

    }

    public void updateThresholds(long deviceId, Thresholds thresholds) {
        DeviceAPI deviceAPI = ServiceGenerator.getDeviceAPI();
        Call<ResponseBody> call = deviceAPI.updateThresholds(deviceId, thresholds);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                responseInformation.setValue("Thresholds updated successfully.");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
                responseInformation.setValue("Threshold update failed");
            }
        });
    }

    public void deleteDevice(long deviceID) {
        DeviceAPI deviceAPI = ServiceGenerator.getDeviceAPI();

        User user = UserManager.getInstance().getUser();
        Call<ResponseBody> call = deviceAPI.deleteDevice(user.getUserID(), deviceID, user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    responseInformation.setValue("Device deleted successfully.");
                    getAllDevices(user.getUserID());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
                responseInformation.setValue("Encountered an error trying to delete device.");
            }
        });
    }

    public MutableLiveData<String> getResponseInformation() {
        return responseInformation;
    }

    public MutableLiveData<List<Device>> getDevices() {
        return devices;
    }
}
