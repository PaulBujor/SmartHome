package com.example.myhomeapplication.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.CO2ViewModel;
import com.example.myhomeapplication.ViewModel.HumidityViewModel;
import com.example.myhomeapplication.ViewModel.MotionViewModel;
import com.example.myhomeapplication.ViewModel.TemperatureViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button temperatureCardButton,humidityCardButton, c02CardButton, motionCardButton;
    private TextView temperatureTextView, humidityTextView, cO2TextView,motionTextView;

    private TemperatureViewModel temperatureViewModel;
    private HumidityViewModel humidityViewModel;
    private CO2ViewModel CO2ViewModel;
    private MotionViewModel motionViewModel;

    private int deviceID = 1;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        temperatureCardButton = view.findViewById(R.id.temperatureCardButton);
        humidityCardButton = view.findViewById(R.id.humidityCardButton);
        motionCardButton = view.findViewById(R.id.motionCardButton);
        c02CardButton = view.findViewById(R.id.C02CardButton);

        temperatureCardButton.setOnClickListener((v) -> changeFragment("TemperatureDetails"));
        humidityCardButton.setOnClickListener((v) -> changeFragment("HumidityDetails"));
        motionCardButton.setOnClickListener((v) -> changeFragment("MotionDetails"));
        c02CardButton.setOnClickListener((v)-> changeFragment("C02Details"));

        temperatureTextView = view.findViewById(R.id.temperatureCardValue);
        humidityTextView = view.findViewById(R.id.humidityCardValue);
        cO2TextView = view.findViewById(R.id.C02CardValue);
        motionTextView = view.findViewById(R.id.motionCardValue);

        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        motionViewModel = new ViewModelProvider(this).get(MotionViewModel.class);

        temperatureViewModel.getLatestTemperatureMeasurement().observe(getViewLifecycleOwner(), measurement -> temperatureTextView.setText(String.format("%.1f", measurement.getValue())));
        humidityViewModel.getLastMeasurement().observe(getViewLifecycleOwner(), measurement -> humidityTextView.setText(String.format("%.1f", measurement.getValue())));
        motionViewModel.getLastMotionMeasurement().observe(getViewLifecycleOwner(),measurement -> motionTextView.setText(String.format("%.1f", measurement.getValue())));

        //Obsolete DataRetriever
        /*DataRetriever dataRetriever = DataRetriever.getInstance();
        dataRetriever.startRetrievingData(deviceID);*/

        return view;
    }

    private void changeFragment(String fragment){
        switch (fragment){
            case "TemperatureDetails":{
                NavHostFragment.findNavController(this).navigate(R.id.openTemperatureDetailsAction);
            }break;
            case "HumidityDetails":{
                NavHostFragment.findNavController(this).navigate(R.id.openHumidityDetailsAction);
            }break;
            case "MotionDetails":{
                NavHostFragment.findNavController(this).navigate(R.id.openMotionDetailsAction);
            }break;
            case "C02Details":{
                NavHostFragment.findNavController(this).navigate(R.id.openCO2DetailsAction);
            }break;
        }
    }
}