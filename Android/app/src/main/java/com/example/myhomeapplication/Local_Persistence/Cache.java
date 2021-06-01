package com.example.myhomeapplication.Local_Persistence;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myhomeapplication.Authorization.UserManager;
import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.EUser;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Models.Thresholds;
import com.example.myhomeapplication.Models.User;
import com.example.myhomeapplication.Remote.Clients.DeviceClient;
import com.example.myhomeapplication.Remote.Clients.MeasurementClient;
import com.example.myhomeapplication.Remote.DeviceAPI;
import com.example.myhomeapplication.Remote.MeasurementAPI;
import com.example.myhomeapplication.Remote.ServiceGenerator;

import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Cache {

    private static Cache instance;
    private MeasurementClient measurementClient;
    private DeviceClient deviceClient;

    private final MutableLiveData<Measurement> latestTemperatureMeasurement;
    private final MutableLiveData<Measurement> latestHumidityMeasurement;
    private final MutableLiveData<Measurement> latestCO2Measurement;
    private final MutableLiveData<Measurement> latestSoundMeasurement;
    private final MutableLiveData<List<Device>> devices;
    private final MutableLiveData<Thresholds> thresholds;
    private MutableLiveData<String> responseInformation;
    private MutableLiveData<Long> deviceID;


    private Cache() {
        latestTemperatureMeasurement = new MutableLiveData<>();
        latestHumidityMeasurement = new MutableLiveData<>();
        latestCO2Measurement = new MutableLiveData<>();
        latestSoundMeasurement = new MutableLiveData<>();
        devices = new MutableLiveData<>();
        thresholds = new MutableLiveData<>();
        responseInformation = new MutableLiveData<>();
        initClients();
        responseInformation = deviceClient.getResponseInformation();
    }

    public void initClients() {
        measurementClient = MeasurementClient.getInstance();
        deviceClient = DeviceClient.getInstance();
    }

    public static synchronized Cache getInstance() {
        if (instance == null)
            instance = new Cache();
        return instance;
    }

    public LiveData<Measurement> getLatestTemperatureMeasurement() {
        return latestTemperatureMeasurement;
    }

    public LiveData<Measurement> getLatestHumidityMeasurement() {
        return latestHumidityMeasurement;
    }

    public LiveData<Measurement> getLatestCO2Measurement() {
        return latestCO2Measurement;
    }

    public LiveData<Measurement> getLatestSoundMeasurement() {
        return latestSoundMeasurement;
    }

    public MutableLiveData<List<Device>> getDevices() {
        return devices;
    }

    public MutableLiveData<Thresholds> getThresholds() {
        return thresholds;
    }

    //TODO remove
    /*public LiveData<List<Measurement>> getAllMeasurements(int deviceID, String measurementType) {
        MutableLiveData<List<Measurement>> allMeasurements = new MutableLiveData<>();

        MeasurementAPI measurementAPI = ServiceGenerator.getMeasurementAPI();
        Call<List<Measurement>> call = measurementAPI.getMeasurements(deviceID, measurementType);
        call.enqueue(new Callback<List<Measurement>>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                if (response.code() == 200) {
                    List<Measurement> sortedMeasurements = response.body();
                    Collections.sort(sortedMeasurements);
                    allMeasurements.setValue(sortedMeasurements);
                    Log.i("HTTPResponseCode", String.valueOf(response.code()) + " " + measurementType);
                } else {
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code() + " " + measurementType + "\n" + response.message()));
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });

        return allMeasurements;
    }*/

    public LiveData<List<Measurement>> getAllMeasurements(int deviceID, String measurementType) {
        return measurementClient.getAllMeasurements(deviceID, measurementType);
    }

    //TODO remove
    /*public void receiveLatestMeasurement(int deviceID, String measurementType) {
        MeasurementAPI measurementAPI = ServiceGenerator.getMeasurementAPI();
        Call<Measurement> call = measurementAPI.getLatestMeasurement(deviceID, measurementType);
        call.enqueue(new Callback<Measurement>() {

            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Measurement> call, Response<Measurement> response) {
                if (response.code() == 200) {
                    switch (measurementType) {
                        case MeasurementTypes.TYPE_TEMPERATURE:
                            latestTemperatureMeasurement.setValue(response.body());
                            break;
                        case MeasurementTypes.TYPE_HUMIDITY:
                            latestHumidityMeasurement.setValue(response.body());
                            break;
                        case MeasurementTypes.TYPE_CO2:
                            latestCO2Measurement.setValue(response.body());
                            break;
                        case MeasurementTypes.TYPE_SOUND:
                            latestSoundMeasurement.setValue(response.body());
                            break;
                        default:
                            Log.wtf("Repository", "Wrong measurement type");
                    }
                    Log.i("HTTPResponseCode", String.valueOf(response.code()) + " " + measurementType);
                } else {
                    Log.i("HTTPResponseCodeFAILURE", String.valueOf(response.code() + " " + measurementType + "\n" + response.message()));
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<Measurement> call, Throwable t) {
                Log.i("Retrofit", "Something went wrong :(");
                Log.i("Retrofit", t.getMessage());
                t.printStackTrace();
            }
        });
    }*/

    public void receiveLatestMeasurement(int deviceID, String measurementType) {

        switch (measurementType) {
            case MeasurementTypes.TYPE_TEMPERATURE:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestTemperatureMeasurement);
                break;
            case MeasurementTypes.TYPE_HUMIDITY:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestHumidityMeasurement);
                break;
            case MeasurementTypes.TYPE_CO2:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestCO2Measurement);
                break;
            case MeasurementTypes.TYPE_SOUND:
                measurementClient.receiveLatestMeasurement(deviceID, measurementType, latestSoundMeasurement);
                break;
            default:
                Log.wtf("Repository", "Wrong measurement type");
        }
    }

    //TODO remove
    /*public void getAllDevices(long userID) {
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
    }*/

    public void getAllDevices(long userID) {
        deviceClient.getAllDevices(userID).observeForever(list -> devices.setValue(list));
    }

    //TODO remove
    /*public void getThresholdsByDevice(long deviceID) {
        DeviceAPI deviceAPI = ServiceGenerator.getDeviceAPI();
        Call<Thresholds> call = deviceAPI.getThresholdsByDevice(deviceID);
        call.enqueue(new Callback<Thresholds>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<Thresholds> call, Response<Thresholds> response) {
                if (response.code() == 200) {
                    thresholds.setValue(response.body());
                    responseInformation.setValue("Thresholds displayed for device : " + deviceID);
                    Log.i("TESTING", "MIN TEMPERATURE ::: " + String.valueOf(thresholds.getValue().getMinTemperature()));
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

    }*/

    public void getThresholdsByDevice(long deviceID) {
        deviceClient.getThresholdsByDevice(deviceID).observeForever(thresholds::setValue);
    }

    //TODO remove
    /*public void addDevice(Device tmpDevice) {
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

    }*/

    public void addDevice(Device tmpDevice) {
        deviceClient.addDevice(tmpDevice);
    }

    //TODO remove
    /*public void updateThresholds(long deviceId, Thresholds thresholds) {
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
    }*/

    public void updateThresholds(long deviceId, Thresholds thresholds) {
        deviceClient.updateThresholds(deviceId, thresholds);
    }

    //TODO remove
    /*public void deleteDevice(long deviceID) {
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
    }*/

    public void deleteDevice(long deviceID) {
        deviceClient.deleteDevice(deviceID);
    }

    public MutableLiveData<String> getResponseInformation() {
        return responseInformation;
    }


    public void setLatestTemperatureMeasurement(Measurement m) {
        latestTemperatureMeasurement.setValue(m);
    }

    public void setLatestHumidityMeasurement(Measurement m) {
        latestHumidityMeasurement.setValue(m);
    }

    public void setLatestCO2Measurement(Measurement m) {
        latestCO2Measurement.setValue(m);
    }

    public void setLatestSoundMeasurement(Measurement m) {
        latestCO2Measurement.setValue(m);
    }

    public void setDevices(List<Device> l) {
        devices.setValue(l);
    }

    public void setThresholds(Thresholds t) {
        thresholds.setValue(t);
    }

    public void setResponseInformation(String s) {
        responseInformation.setValue(s);


    public MutableLiveData<Long> getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID.setValue(deviceID);

    }
}
