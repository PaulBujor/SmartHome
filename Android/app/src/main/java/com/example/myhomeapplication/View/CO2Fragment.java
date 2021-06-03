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
import com.example.myhomeapplication.ViewModel.CO2ViewModel;
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
import java.util.stream.Collectors;

public class CO2Fragment extends Fragment {
    private CO2ViewModel viewModel;
    private RecyclerView recyclerView;
    private LineChart cO2Graph;
    private long deviceId =420;
    public CO2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_o2, container, false);

        final Observer<List<Measurement>> allMeasurementsObserver = measurements -> {
            // take last 100
            List<Measurement> limitedList = measurements.stream().skip(measurements.size() - 100).collect(Collectors.toList());

            LineData newLineData = getLineData(limitedList);
            cO2Graph.setData(newLineData); 
            cO2Graph.invalidate(); 

            RecyclerAdapter newAdapter = new RecyclerAdapter(measurements);
            recyclerView.setAdapter(newAdapter);
        };
        viewModel = new ViewModelProvider(this).get(CO2ViewModel.class);
        viewModel.getDeviceID().observeForever(i -> deviceId = i);
        viewModel.getAllMeasurements(deviceId, MeasurementTypes.TYPE_CO2).observe(getViewLifecycleOwner(), allMeasurementsObserver);
        
        cO2Graph = view.findViewById(R.id.cO2DetailsGraph);
        LineDataSet lineDataSet = new LineDataSet(null, "CO2 Measurements Set");
        
        LineData lineData = new LineData((ILineDataSet) lineDataSet);
        cO2Graph.setData(lineData);
        
        
        cO2Graph.setDrawGridBackground(false);
        cO2Graph.setDrawBorders(false);
        cO2Graph.setDescription(null);
        cO2Graph.getLegend().setEnabled(false);
        cO2Graph.setScaleYEnabled(false);
        cO2Graph.setExtraLeftOffset(10);
        cO2Graph.setExtraRightOffset(30);

        XAxis xAxis = cO2Graph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new XAxisValueFormatter());
        xAxis.setLabelCount(5, true);

        YAxis yr = cO2Graph.getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);

        YAxis yl = cO2Graph.getAxisLeft();
        yl.setSpaceTop(30);

        //Marker
        IMarker marker = new CustomMarkerView(getContext(), R.layout.graph_marker);
        cO2Graph.setMarker(marker);

        recyclerView = view.findViewById(R.id.recyclerViewCO2Details);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAllMeasurements(deviceId,MeasurementTypes.TYPE_CO2).observe(getViewLifecycleOwner(), measurements -> {
            for (Measurement m: measurements){
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
        float millis = df.getMillis();
        return millis;
    }
}