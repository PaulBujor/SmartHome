package com.example.myhomeapplication.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.example.myhomeapplication.Models.Device;
import com.example.myhomeapplication.Models.DeviceAdapter;
import com.example.myhomeapplication.Models.DeviceItem;
import com.example.myhomeapplication.Models.Thresholds;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.DeviceSettingsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DeviceSettingsFragment extends Fragment implements DeviceAdapter.OnDeviceItemClickedListener, View.OnClickListener {

    private View view;
    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
    private EditText tempMin, tempMax, humMin, humMax, cO2min, cO2max, soundMin, soundMax;
    private Button addDevice, removeDevice;
    private DeviceSettingsViewModel deviceSettingsViewModel;

    public DeviceSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        deviceSettingsViewModel = new ViewModelProvider(this).get(DeviceSettingsViewModel.class);
        deviceSettingsViewModel.getDevicesMutable().observe(this,devices -> {
            deviceAdapter.updateAdapter(deviceSettingsViewModel.getAllDevices());
        });

        deviceSettingsViewModel.getThresholdsMutable().observe(this, thresholds -> {

            if(thresholds!=null) {
                tempMin.setText(String.valueOf(thresholds.getMinTemperature()));
                tempMax.setText(String.valueOf(thresholds.getMaxTemperature()));;
                humMin.setText(String.valueOf(thresholds.getMinHumidity()));
                humMax.setText(String.valueOf(thresholds.getMaxHumidity()));
                cO2min.setText(String.valueOf(thresholds.getMinCO2()));
                cO2max.setText(String.valueOf(thresholds.getMaxCO2()));
               /* soundMin.setText(String.valueOf(thresholds.));*/
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
        soundMin = view.findViewById(R.id.ds_sound_min_edit);
        soundMax = view.findViewById(R.id.ds_sound_max_edit);
        addDevice = view.findViewById(R.id.addDevice_button);
        removeDevice = view.findViewById(R.id.removeDevice_button);
        recyclerView = view.findViewById(R.id.ds_recycler_view);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addDevice.setOnClickListener(this);
        removeDevice.setOnClickListener(this);
        removeDevice.setVisibility(View.GONE);

        ArrayList<DeviceItem> tmp = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(tmp,this);
        recyclerView.setAdapter(deviceAdapter);

       /* tempMin.addTextChangedListener(thresholdWatcher);
        tempMax.addTextChangedListener(thresholdWatcher);
        humMin.addTextChangedListener(thresholdWatcher);
        humMax.addTextChangedListener(thresholdWatcher);
        cO2min.addTextChangedListener(thresholdWatcher);
        cO2max.addTextChangedListener(thresholdWatcher);
        soundMin.addTextChangedListener(thresholdWatcher);
        soundMax.addTextChangedListener(thresholdWatcher);*/

        //TODO (from Alex) you can use TextWatcher to activate the button to save threshold changes and also to validate the input



        return view;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addDevice_button:
                //TODO open a smaller window to add ID or to make a editfield visible to add ID and the change the button to save
                return;
            case R.id.removeDevice_button:
                //TODO delete device
                return;
        }
    }

    //On recyclerView item clicked listener
    @Override
    public void onItemClicked(String id) {

        removeDevice.setVisibility(View.VISIBLE);
        Log.d("TESTLISTENER", "onItemClicked: "+id);
        deviceSettingsViewModel.getThresholds(id);
        //TODO get settings from repository by device ID and set editfields to the values and maybe add a button to save changes
    }

    private TextWatcher thresholdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    }

