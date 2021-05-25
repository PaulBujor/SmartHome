package com.example.myhomeapplication.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myhomeapplication.Models.DeviceAdapter;
import com.example.myhomeapplication.Models.DeviceItem;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.DeviceSettingsViewModel;

import java.util.ArrayList;

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
        //soundMin = view.findViewById(R.id.ds_sound_min);
        //soundMax = view.findViewById(R.id.ds_sound_max);
        addDevice = view.findViewById(R.id.addDevice_button);
        removeDevice = view.findViewById(R.id.removeDevice_button);
        recyclerView = view.findViewById(R.id.ds_recycler_view);

        addDevice.setOnClickListener(this);
        removeDevice.setOnClickListener(this);
        removeDevice.setVisibility(View.GONE);

       /* tempMin.addTextChangedListener(thresholdWatcher);
        tempMax.addTextChangedListener(thresholdWatcher);
        humMin.addTextChangedListener(thresholdWatcher);
        humMax.addTextChangedListener(thresholdWatcher);
        cO2min.addTextChangedListener(thresholdWatcher);
        cO2max.addTextChangedListener(thresholdWatcher);
        soundMin.addTextChangedListener(thresholdWatcher);
        soundMax.addTextChangedListener(thresholdWatcher);*/

        //TODO (from Alex) you can use TextWatcher to activate the button to save threshold changes and also to validate the input

        init();
      /*  getDevices();*/
        return view;
    }

    //TODO to be called in OnCreate
    public void init() {
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO get this from repository instead
    /*    ArrayList<DeviceItem> deviceItems = new ArrayList<>();
        deviceItems.add(new DeviceItem("lol","lolz"));
        deviceAdapter = new DeviceAdapter(deviceItems, this);
        recyclerView.setAdapter(deviceAdapter);*/
        deviceAdapter = new DeviceAdapter(deviceSettingsViewModel.getAllDevices(), this);
        recyclerView.setAdapter(deviceAdapter);
    }

 /*   public void getDevices(){

        deviceAdapter = new DeviceAdapter(deviceSettingsViewModel.getAllDevices(), this);
        recyclerView.setAdapter(deviceAdapter);
    }*/



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