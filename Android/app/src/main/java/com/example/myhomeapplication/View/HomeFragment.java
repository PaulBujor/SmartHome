package com.example.myhomeapplication.View;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.CO2ViewModel;
import com.example.myhomeapplication.ViewModel.HumidityViewModel;
import com.example.myhomeapplication.ViewModel.SoundViewModel;
import com.example.myhomeapplication.ViewModel.TemperatureViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private Button temperatureCardButton, humidityCardButton, c02CardButton, motionCardButton;
    private TextView temperatureTextView, humidityTextView, cO2TextView, soundTextView;
    private TextView temperatureUnit;

    private TemperatureViewModel temperatureViewModel;
    private HumidityViewModel humidityViewModel;
    private CO2ViewModel co2ViewModel;
    private SoundViewModel soundViewModel;

    private SharedPreferences sharedPreferences;

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
        c02CardButton.setOnClickListener((v) -> changeFragment("C02Details"));

        temperatureTextView = view.findViewById(R.id.temperatureCardValue);
        humidityTextView = view.findViewById(R.id.humidityCardValue);
        cO2TextView = view.findViewById(R.id.C02CardValue);
        soundTextView = view.findViewById(R.id.motionCardValue);
        temperatureUnit = view.findViewById(R.id.temperatureCardUnit);

        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        soundViewModel = new ViewModelProvider(this).get(SoundViewModel.class);
        co2ViewModel = new ViewModelProvider(this).get(CO2ViewModel.class);

        sharedPreferences = MainActivity.sharedPreferences;
        String system = sharedPreferences.getString("sh_system", "null");

        temperatureViewModel.getLatestTemperatureMeasurement().observe(getViewLifecycleOwner(), measurement ->
        {
            switch (system) {
                case "metric":
                    temperatureUnit.setText(getString(R.string.metric_symbol));
                    temperatureTextView.setText(String.format("%.1f", measurement.getValue()));
                    break;
                case "imperial":
                    temperatureUnit.setText(getString(R.string.imperial_symbol));
                    double f = (measurement.getValue() * 1.8) + 32;
                    Log.wtf("UNIT", String.format("%.1f", f));
                    temperatureTextView.setText(String.format("%.1f", f));
                    break;
                default:
                    Log.wtf("SHARED_PREFERENCES", "null");
            }
        });
        co2ViewModel.getLatestCO2Measurement().observe(getViewLifecycleOwner(), measurement -> cO2TextView.setText(String.valueOf((int) measurement.getValue())));
        humidityViewModel.getLatestHumidityMeasurement().observe(getViewLifecycleOwner(), measurement -> humidityTextView.setText(String.format("%.1f", measurement.getValue())));
        soundViewModel.getLatestSoundMeasurement().observe(getViewLifecycleOwner(), measurement -> soundTextView.setText(String.format("%.1f", measurement.getValue())));

        //Obsolete DataRetriever
        /*DataRetriever dataRetriever = DataRetriever.getInstance();
        dataRetriever.startRetrievingData(deviceID);*/

        return view;
    }

    private void changeFragment(String fragment) {
        switch (fragment) {
            case "TemperatureDetails": {
                NavHostFragment.findNavController(this).navigate(R.id.openTemperatureDetailsAction);
            }
            break;
            case "HumidityDetails": {
                NavHostFragment.findNavController(this).navigate(R.id.openHumidityDetailsAction);
            }
            break;
            case "MotionDetails": {
                NavHostFragment.findNavController(this).navigate(R.id.openMotionDetailsAction);
            }
            break;
            case "C02Details": {
                NavHostFragment.findNavController(this).navigate(R.id.openCO2DetailsAction);
            }
            break;
        }
    }
}