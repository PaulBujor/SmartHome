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

import com.example.myhomeapplication.Custom.CustomMarkerView;
import com.example.myhomeapplication.Custom.XAxisValueFormatter;
import com.example.myhomeapplication.Local_Persistence.MeasurementTypes;
import com.example.myhomeapplication.Models.Measurement;
import com.example.myhomeapplication.Custom.RecyclerAdapter;
import com.example.myhomeapplication.R;
import com.example.myhomeapplication.ViewModel.HumidityViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
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

public class HumidityFragment extends Fragment {
    private HumidityViewModel humidityViewModel;
    private RecyclerView recyclerView;
    private LineChart humidityGraph;
    private int deviceId = 420;


    public HumidityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_humidity, container, false);

        final Observer<List<Measurement>> allMeasurementsObserver = measurements ->{
            LineData newLineData = getLineData(measurements);
            humidityGraph.setData(newLineData);
            humidityGraph.invalidate();

            RecyclerAdapter newAdapter = new RecyclerAdapter(measurements);
            recyclerView.setAdapter(newAdapter);
        };
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        humidityViewModel.getAllMeasurements(deviceId, MeasurementTypes.TYPE_HUMIDITY).observe(getViewLifecycleOwner(), allMeasurementsObserver);

        humidityGraph = view.findViewById(R.id.humidityDetailsGraph);
        LineDataSet lineDataSet = new LineDataSet(null,"Humidity Measurement Set");

        LineData lineData = new LineData((ILineDataSet) lineDataSet);
        humidityGraph.setData(lineData);

        humidityGraph.setDrawGridBackground(false);
        humidityGraph.setDrawBorders(false);
        humidityGraph.setDescription(null);
        humidityGraph.getLegend().setEnabled(false);
        humidityGraph.setScaleYEnabled(false);
        //To fix labels going off the screen
        humidityGraph.setExtraLeftOffset(10);
        humidityGraph.setExtraRightOffset(30);

        XAxis xAxis = humidityGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new XAxisValueFormatter());
        xAxis.setLabelCount(5,true);

        YAxis yAxisR = humidityGraph.getAxisRight();
        yAxisR.setEnabled(false);
        yAxisR.setDrawAxisLine(false);

        YAxis yAxisL = humidityGraph.getAxisLeft();
        yAxisL.setSpaceTop(30);
        //yAxisL.setSpaceBottom(20);

        //Marker
        IMarker marker = new CustomMarkerView(getContext(), R.layout.graph_marker);
        humidityGraph.setMarker(marker);
        
        recyclerView = view.findViewById(R.id.recyclerViewHumidityDetails);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        humidityViewModel.getAllMeasurements(deviceId,MeasurementTypes.TYPE_HUMIDITY).observe(getViewLifecycleOwner(), measurements -> {
            for (Measurement m : measurements){
                Log.d("TestingMeasurements",String.valueOf(m.getMeasurementID()) + String.valueOf(m.getTimestamp() + " " + String.valueOf(m.getValue())));
            }
        });
        
        return view;
    }

    private LineData getLineData(List<Measurement> measurements) {
        ArrayList<Entry>values = new ArrayList<>();
        for (Measurement m : measurements){
            values.add(new Entry(getMilliseconds(m.getTimestamp()),(float) m.getValue()));
        }
        LineDataSet set1 = new LineDataSet(values, "Temperature Measurements Set");
        set1.setLineWidth(4f);
        set1.setDrawCircles(false);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawValues(false);
        set1.setDrawFilled(true);
        set1.setColor(Color.parseColor("#4B6C53"));
        set1.setHighLightColor(Color.parseColor("#577d61"));
        set1.setHighlightLineWidth(2f);
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