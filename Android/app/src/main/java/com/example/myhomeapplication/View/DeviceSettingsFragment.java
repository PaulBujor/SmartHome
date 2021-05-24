package com.example.myhomeapplication.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myhomeapplication.Models.DeviceAdapter;
import com.example.myhomeapplication.Models.DeviceItem;
import com.example.myhomeapplication.R;

import java.util.ArrayList;

public class DeviceSettingsFragment extends Fragment implements DeviceAdapter.OnDeviceItemClickedListener, View.OnClickListener {

    private View view;
    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
    private EditText tempMin, tempMax, humMin, humMax, cO2min, cO2max, soundMin, soundMax;
    private Button addDevice, removeDevice;

    public DeviceSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        soundMin = view.findViewById(R.id.ds_sound_min);
        soundMax = view.findViewById(R.id.ds_sound_max);
        addDevice = view.findViewById(R.id.addDevice_button);
        removeDevice = view.findViewById(R.id.removeDevice_button);

        addDevice.setOnClickListener(this);
        removeDevice.setOnClickListener(this);
        removeDevice.setVisibility(View.GONE);

        return view;
    }

    //TODO to be called in OnCreate
    public void init() {
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO get this from repository instead
        ArrayList<DeviceItem> deviceItems = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(deviceItems, this);
        recyclerView.setAdapter(deviceAdapter);
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
        //TODO get settings from repository by device ID and set editfields to the values and maybe add a button to save changes
    }
}