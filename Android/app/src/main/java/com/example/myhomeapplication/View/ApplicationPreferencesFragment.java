package com.example.myhomeapplication.View;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhomeapplication.R;

import org.w3c.dom.Text;


public class ApplicationPreferencesFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private ConstraintLayout metric, imperial;
    private TextView metricSymbol, metricText;
    private TextView imperialSymbol, imperialText;


    public ApplicationPreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_application_preferences, container, false);

        sharedPreferences = MainActivity.sharedPreferences;
        metric = view.findViewById(R.id.metric);
        imperial = view.findViewById(R.id.imperial);
        metricSymbol = view.findViewById(R.id.metric_symbol);
        metricText = view.findViewById(R.id.metric_title);
        imperialSymbol = view.findViewById(R.id.imperial_symbol);
        imperialText = view.findViewById(R.id.imperial_title);

        if (sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("metric"))
        {
            metricSymbol.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            metricText.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            imperialSymbol.setTextColor(Color.parseColor("#808080"));
            imperialText.setTextColor(Color.parseColor("#808080"));
        }
        else if (sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("imperial")) {
            imperialSymbol.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            imperialText.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            metricSymbol.setTextColor(Color.parseColor("#808080"));
            metricText.setTextColor(Color.parseColor("#808080"));
        }
        else {
            Log.wtf("SHARED_PREFERENCES", "got null");
        }


        metric.setOnClickListener(v -> {
            boolean isAlready = sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("metric") &&
                    !sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("null");
            if (!isAlready)
                Toast.makeText(getContext(), "System of Measurement changed to Metric", Toast.LENGTH_SHORT).show();

            sharedPreferences.edit().putString("sh_system", "metric").apply();
            metricSymbol.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            metricText.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            imperialSymbol.setTextColor(Color.parseColor("#808080"));
            imperialText.setTextColor(Color.parseColor("#808080"));
        });

        imperial.setOnClickListener(v -> {
            boolean isAlready = sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("imperial") &&
                    !sharedPreferences.getString("sh_system", "null").equalsIgnoreCase("null");
            if (!isAlready)
                Toast.makeText(getContext(), "System of Measurement changed to Imperial", Toast.LENGTH_SHORT).show();

            sharedPreferences.edit().putString("sh_system", "imperial").apply();
            imperialSymbol.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            imperialText.setTextColor(ContextCompat.getColor(getContext(), R.color.green_pale));
            metricSymbol.setTextColor(Color.parseColor("#808080"));
            metricText.setTextColor(Color.parseColor("#808080"));
        });


        return view;
    }
}