package com.example.myhomeapplication.View;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myhomeapplication.Models.Measurement;
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

    private Button temperatureCardButton;
    private TextView temperatureTextView;

    private TemperatureViewModel temperatureViewModel;
    private HumidityViewModel humidityViewModel;
    private CO2ViewModel CO2ViewModel;
    private MotionViewModel motionViewModel;

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
        temperatureCardButton.setOnClickListener((v) -> temperatureDetails(v));

        temperatureTextView = view.findViewById(R.id.temperatureCardValue);

        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);

        temperatureViewModel.getLatestTemperatureMeasurement().observe(getViewLifecycleOwner(), measurement -> temperatureTextView.setText(String.format("%.1f", measurement.getValue())));

        temperatureViewModel.receiveLatestTemperatureMeasurement();

        //Retrieving data simulation

        /*
        temperatureViewModel.getAllTemperatureMeasurements().observe(getViewLifecycleOwner(), measurements -> temperatureTextView.setText(String.format("%.1f", measurements.get(measurements.size()-1).getValue())));
        try {
            temperatureViewModel.addDataSimulation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        return view;
    }

    private void temperatureDetails(View v) {
        NavHostFragment.findNavController(this).navigate(R.id.openTemperatureDetailsAction);
    }
}