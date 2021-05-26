package com.example.myhomeapplication.View;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhomeapplication.Custom.XAxisValueFormatter;
import com.example.myhomeapplication.Local_Persistence.MeasurementTypes;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Models.RecyclerAdapter;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.TemperatureViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class TemperatureFragment extends Fragment {

    private TemperatureViewModel temperatureViewModel;
    private RecyclerView recyclerView;
    private LineChart temperatureGraph;
    private int deviceID = 420;

    public TemperatureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temperature, container, false);

        final Observer<List<Measurement>> allMeasurementsObserver = measurements -> {
            // take first 100
            // List<Measurement> limitedList = measurements.stream().limit(100).collect(Collectors.toList());

            // take last 100
            List<Measurement> limitedList = measurements.stream().skip(measurements.size() - 100).collect(Collectors.toList());

            // update graph
            LineData newLineData = getLineData(limitedList);
            temperatureGraph.setData(newLineData); //might need to do .clearData() for lineData from onCreateView
            temperatureGraph.invalidate(); //refreshes graph

            //set newly updated adapter
            RecyclerAdapter newAdapter = new RecyclerAdapter(measurements);
            recyclerView.setAdapter(newAdapter);
        };

        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        temperatureViewModel.getAllMeasurements(deviceID, MeasurementTypes.TYPE_ALL_TEMPERATURES).observe(getViewLifecycleOwner(), allMeasurementsObserver);

        temperatureGraph = view.findViewById(R.id.temperatureDetailsGraph);
        LineDataSet lineDataSet = new LineDataSet(null, "Temperature Measurements Set");

        LineData lineData = new LineData((ILineDataSet) lineDataSet);
        temperatureGraph.setData(lineData);

        //Styling
        temperatureGraph.setDrawGridBackground(false);
        temperatureGraph.setDrawBorders(false);
        temperatureGraph.setDescription(null);
        temperatureGraph.getLegend().setEnabled(false);
        //To fix labels going off the screen
        temperatureGraph.setExtraLeftOffset(5);
        temperatureGraph.setExtraRightOffset(30);

        //XAxis
        XAxis xAxis = temperatureGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new XAxisValueFormatter());
        xAxis.setLabelCount(5, true);
        //xAxis.setXOffset(20f);

        //Right YAxis - disabled
        YAxis yr = temperatureGraph.getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);

        //Left YAxis
        YAxis yl = temperatureGraph.getAxisLeft();
        yl.setSpaceTop(30);
        //yl.setSpaceBottom(20);

        recyclerView = view.findViewById(R.id.recyclerViewTemperatureDetails);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Probably redundant since an observer is set
        // temperatureRecyclerAdapter = new TemperatureRecyclerAdapter(currentAllMeasurements.getValue());
        // recyclerView.setAdapter(temperatureRecyclerAdapter);

        //Testing getAllMeasurements
        temperatureViewModel.getAllMeasurements(deviceID, MeasurementTypes.TYPE_ALL_TEMPERATURES).observe(getViewLifecycleOwner(), measurements -> {
            for (Measurement m : measurements) {
                Log.d("TestingMeasurements", String.valueOf(m.getMeasurementID()) + String.valueOf(m.getTimestamp()) + " " + String.valueOf(m.getValue()));
            }
        });

        return view;
    }

    private LineData getLineData(List<Measurement> measurements) {
        ArrayList<Entry> values = new ArrayList<>();

        for (Measurement m : measurements) {
            values.add(new Entry(getMilliseconds(m.getTimestamp()), (float) m.getValue()));
        }

        LineDataSet set1 = new LineDataSet(values, "Temperature Measurements Set");
        set1.setLineWidth(4f);
        set1.setCircleRadius(5f);
        set1.setDrawCircles(false);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.parseColor("#4B6C53"));
        set1.setCircleColor(Color.WHITE);
        set1.setCircleHoleColor(Color.parseColor("#4B6C53"));
        set1.setHighLightColor(Color.parseColor("#577d61"));
        set1.setHighlightLineWidth(2f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_green);
        drawable.setAlpha(128);
        set1.setFillDrawable(drawable);


        return new LineData(set1);
    }

    private float getMilliseconds(Date d) {
        DateTime df = new DateTime(d);

        float millis = df.getMillisOfDay();
        Log.d("MILIS", String.valueOf(millis));
        Log.d("MILIS", String.valueOf(df.getMillisOfDay()));
        return millis;
    }
}