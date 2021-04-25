package com.example.myhomeapplication.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myhomeapplication.Local_Persistence.MeasurementTypes;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Models.TemperatureRecyclerAdapter;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.TemperatureViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;


public class TemperatureFragment extends Fragment {

    private TemperatureViewModel temperatureViewModel;
    private RecyclerView recyclerView;
    private TemperatureRecyclerAdapter temperatureRecyclerAdapter;
    private LineChart temperatureGraph;
    private int deviceID = 1;
    public TemperatureFragment() {
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
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        final Observer<List<Measurement>> allMeasurementsObserver = new Observer<List<Measurement>>() {
            @Override
            public void onChanged(List<Measurement> measurements) {
                //TODO update graph

                //set newly updated adapter
                TemperatureRecyclerAdapter newAdapter = new TemperatureRecyclerAdapter(measurements);
                recyclerView.setAdapter(newAdapter);
            }
        };


        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        temperatureViewModel.getAllMeasurements(deviceID, MeasurementTypes.TYPE_ALL_TEMPERATURES).observe(getViewLifecycleOwner(), allMeasurementsObserver);

        LiveData<List<Measurement>> currentAllMeasurements = temperatureViewModel.getAllMeasurements(deviceID,MeasurementTypes.TYPE_ALL_TEMPERATURES);


        temperatureGraph = view.findViewById(R.id.temperatureDetailsGraph);
        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(), "Temperature Measurements Set");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        temperatureGraph.setData(lineData);

        //Styling
        temperatureGraph.setDrawGridBackground(true);
        temperatureGraph.setDrawBorders(true);

        XAxis xAxis = temperatureGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisValueFormatter());


        temperatureGraph.invalidate();



        recyclerView = view.findViewById(R.id.recyclerViewTemperatureDetails);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Probably redundant since an observer is set
        // temperatureRecyclerAdapter = new TemperatureRecyclerAdapter(currentAllMeasurements.getValue());
        // recyclerView.setAdapter(temperatureRecyclerAdapter);


        //Testing getAllMeasurements
        temperatureViewModel.getAllMeasurements(deviceID, MeasurementTypes.TYPE_ALL_TEMPERATURES).observe(getViewLifecycleOwner(), measurements -> {
            for (Measurement m : measurements)
            {
                Log.d("TestingMeasurements", String.valueOf(m.getMeasurementID()) + String.valueOf(m.getTimestamp()) + " " + String.valueOf(m.getValue()));
            }
        });



        return view;
    }

    private ArrayList<Entry> lineChartDataSet() {
        ArrayList<Entry> dataSet = new ArrayList<Entry>();

        float tempStart = 25;
        float i = 43200000;

        for (int index = 0; index < 5; index++)
        {
            i+=300000;
            dataSet.add(new Entry(i, tempStart+=2));

        }

        return dataSet;
    }
}