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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceSettingsFragment extends Fragment implements DeviceAdapter.OnDeviceItemClickedListener, View.OnClickListener {

    private View view;
    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;
    private EditText tempMin, tempMax, humMin, humMax, cO2min, cO2max, soundMin, soundMax;
    private Button addDevice, removeDevice;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DeviceSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceSettingsFragment newInstance(String param1, String param2) {
        DeviceSettingsFragment fragment = new DeviceSettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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