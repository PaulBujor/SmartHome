package com.example.myhomeapplication.View;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.DeviceAdapter;
import com.example.myhomeapplication.Models.DeviceItem;
import com.example.myhomeapplication.Models.Thresholds;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.DeviceSettingsViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import static android.view.View.GONE;

public class DeviceSettingsFragment extends Fragment implements DeviceAdapter.OnDeviceItemClickedListener, View.OnClickListener {

    private View view;
    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
    private EditText tempMin, tempMax, humMin, humMax, cO2min, cO2max;
    private Button addDevice, removeDevice, cancelAddDevice, confirmAddDevice, saveChanges;
    private Switch activeSwitch;
    private DeviceSettingsViewModel deviceSettingsViewModel;
    private ConstraintLayout addDeviceConstraintLayout, deviceSettingsConstraintLayout;
    private TextInputEditText deviceNameInput, deviceIDInput;

    public DeviceSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        deviceSettingsViewModel = new ViewModelProvider(this).get(DeviceSettingsViewModel.class);
        deviceSettingsViewModel.getDevicesMutable().observe(this, devices -> {
            deviceAdapter.updateAdapter(deviceSettingsViewModel.getAllDevices());
        });

        deviceSettingsViewModel.getThresholdsMutable().observe(this, thresholds -> {

            if (thresholds != null) {
                tempMin.setText(String.valueOf(thresholds.getMinTemperature()));
                tempMax.setText(String.valueOf(thresholds.getMaxTemperature()));
                humMin.setText(String.valueOf(thresholds.getMinHumidity()));
                humMax.setText(String.valueOf(thresholds.getMaxHumidity()));
                cO2min.setText(String.valueOf(thresholds.getMinCO2()));
                cO2max.setText(String.valueOf(thresholds.getMaxCO2()));
                if (thresholds.isDeviceConfiguration()) {
                    activeSwitch.setChecked(true);
                    activeSwitch.setText(R.string.On);
                } else if (!thresholds.isDeviceConfiguration()) {
                    activeSwitch.setChecked(false);
                    activeSwitch.setText(R.string.Off);
                }

            }
        });

        deviceSettingsViewModel.getResponseInformation().observe(this, message->{
            if(!message.isEmpty()){
                Context context = getContext();
                Toast toast = Toast.makeText(context,message,Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_device_settings_v2, container, false);
        tempMin = view.findViewById(R.id.ds_temp_min);
        tempMax = view.findViewById(R.id.ds_temp_max);
        humMin = view.findViewById(R.id.ds_humidity_min);
        humMax = view.findViewById(R.id.ds_humidity_max);
        cO2min = view.findViewById(R.id.ds_co2_min);
        cO2max = view.findViewById(R.id.ds_co2_max);
        addDevice = view.findViewById(R.id.addDevice_button);
        removeDevice = view.findViewById(R.id.removeDevice_button);
        recyclerView = view.findViewById(R.id.ds_recycler_view);
        addDeviceConstraintLayout = view.findViewById(R.id.addDeviceConstraintLayout);
        deviceSettingsConstraintLayout = view.findViewById(R.id.deviceSettingsConstraintLayout);
        activeSwitch = view.findViewById(R.id.ds_switch);

        saveChanges = view.findViewById(R.id.ds_save_changes_button);
        saveChanges.setVisibility(GONE);

        //Add device
        confirmAddDevice = view.findViewById(R.id.confirmAddDevice);
        cancelAddDevice = view.findViewById(R.id.cancelAddDevice);
        deviceNameInput = view.findViewById(R.id.deviceNameEditInput);
        deviceIDInput = view.findViewById(R.id.deviceIDEditInput);


        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addDevice.setOnClickListener(this);
        removeDevice.setOnClickListener(this);
        removeDevice.setVisibility(GONE);

        ArrayList<DeviceItem> tmp = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(tmp, this);
        recyclerView.setAdapter(deviceAdapter);

        addDeviceConstraintLayout.setVisibility(GONE);

        addDevice.setOnClickListener(v -> {
            addDevice.setVisibility(GONE);
            removeDevice.setVisibility(GONE);
            addDeviceConstraintLayout.setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(deviceSettingsConstraintLayout);
            constraintSet.connect(R.id.manageSensors_textView, ConstraintSet.TOP, R.id.addDeviceConstraintLayout, ConstraintSet.BOTTOM);
            constraintSet.applyTo(deviceSettingsConstraintLayout);

        });

        cancelAddDevice.setOnClickListener(v -> {
            addDeviceConstraintLayout.setVisibility(View.GONE);
            addDevice.setVisibility(View.VISIBLE);
            removeDevice.setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(deviceSettingsConstraintLayout);
            constraintSet.connect(R.id.manageSensors_textView, ConstraintSet.TOP, R.id.addDevice_button, ConstraintSet.BOTTOM);
            constraintSet.applyTo(deviceSettingsConstraintLayout);
        });

        confirmAddDevice.setOnClickListener(this);
        saveChanges.setOnClickListener(this);

        //TextWatcher
        tempMin.addTextChangedListener(thresholdWatcher);
        tempMax.addTextChangedListener(thresholdWatcher);
        humMin.addTextChangedListener(thresholdWatcher);
        humMax.addTextChangedListener(thresholdWatcher);
        cO2min.addTextChangedListener(thresholdWatcher);
        cO2max.addTextChangedListener(thresholdWatcher);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.removeDevice_button:
                deviceSettingsViewModel.deleteDevice(deviceSettingsViewModel.getDeviceIDMutable().getValue());
                return;
            case R.id.confirmAddDevice:
                if (deviceIDInput.getText().toString().isEmpty()) {
                    Context context = getContext();
                    Toast toast = Toast.makeText(context, "Empty field for device ID is not allowed.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                } else {
                    Device tmpDevice = new Device(Long.parseLong(deviceIDInput.getText().toString()), deviceNameInput.getText().toString());
                    deviceSettingsViewModel.addDevice(tmpDevice);

                }
            case R.id.ds_save_changes_button:

                try {

                    Thresholds tmpThreshold = new Thresholds(
                             activeSwitch.isChecked()
                            , Integer.parseInt(humMin.getText().toString())
                            , Integer.parseInt(humMax.getText().toString())
                            , Integer.parseInt(tempMin.getText().toString())
                            , Integer.parseInt(tempMax.getText().toString())
                            , Integer.parseInt(cO2min.getText().toString())
                            , Integer.parseInt(cO2max.getText().toString())
                    );
                    deviceSettingsViewModel.updateThresholds(deviceSettingsViewModel.getDeviceIDMutable().getValue(), tmpThreshold);
                } catch (Exception e) {
                    Log.i("UpdateThresholds", "Something went wrong :(");
                    Log.i("UpdateThresholds", e.getMessage());
                    e.printStackTrace();
                }
        }
    }


    //On recyclerView item clicked listener
    @Override
    public void onItemClicked(String id) {

        removeDevice.setVisibility(View.VISIBLE);
        Log.d("TESTLISTENER", "onItemClicked: " + id);
        deviceSettingsViewModel.getThresholds(id);
        deviceSettingsViewModel.setDeviceIDMutable(Long.parseLong(id));
    }

    private TextWatcher thresholdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String tMin = tempMin.getText().toString();
            String tMax = tempMax.getText().toString();
            String hMin = humMin.getText().toString();
            String hMax = humMax.getText().toString();
            String cMin = cO2min.getText().toString();
            String cMax = cO2max.getText().toString();

            if (tMin.isEmpty() || tMax.isEmpty() || hMin.isEmpty() || hMax.isEmpty() || cMin.isEmpty() || cMax.isEmpty()) {
                saveChanges.setVisibility(GONE);

            } else
                saveChanges.setVisibility(View.VISIBLE);

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}

