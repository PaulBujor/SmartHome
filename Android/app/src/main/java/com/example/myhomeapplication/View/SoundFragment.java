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
import com.example.myhomeapplication.ViewModel.SoundViewModel;
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

public class SoundFragment extends Fragment {
    private SoundViewModel soundViewModel;
    private RecyclerView recyclerView;
    private LineChart motionGraph;
    private long deviceID = 420;

    public SoundFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion, container, false);

        final Observer<List<Measurement>> allMeasurementsObserver = measurements -> {
            // take last 100
            List<Measurement> limitedList = measurements.stream().skip(measurements.size() - 100).collect(Collectors.toList());

            LineData newLineData = getLineData(limitedList);
            motionGraph.setData(newLineData);
            motionGraph.invalidate();

            RecyclerAdapter newAdapter = new RecyclerAdapter(measurements);
            recyclerView.setAdapter(newAdapter);
        };
        soundViewModel = new ViewModelProvider(this).get(SoundViewModel.class);
        soundViewModel.getDeviceID().observeForever(i -> deviceID = i);
        soundViewModel.getAllMeasurements(deviceID, MeasurementTypes.TYPE_ALL_SOUNDS).observe(getViewLifecycleOwner(), allMeasurementsObserver);

        motionGraph = view.findViewById(R.id.motionDetailsGraph);
        LineDataSet lineDataSet = new LineDataSet(null, "Motion Measurements Set");

        LineData lineData = new LineData((ILineDataSet) lineDataSet);
        motionGraph.setData(lineData);


        motionGraph.setDrawGridBackground(false);
        motionGraph.setDrawBorders(false);
        motionGraph.setDescription(null);
        motionGraph.getLegend().setEnabled(false);
        motionGraph.setScaleYEnabled(false);
        motionGraph.setExtraLeftOffset(10);
        motionGraph.setExtraRightOffset(30);


        XAxis xAxis = motionGraph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new XAxisValueFormatter());
        xAxis.setLabelCount(5, true);

        YAxis yr = motionGraph.getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);

        YAxis yl = motionGraph.getAxisLeft();
        yl.setSpaceTop(30);

        //Marker
        IMarker marker = new CustomMarkerView(getContext(), R.layout.graph_marker);
        motionGraph.setMarker(marker);


        recyclerView = view.findViewById(R.id.recyclerViewMotionDetails);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        soundViewModel.getAllMeasurements(deviceID, MeasurementTypes.TYPE_ALL_SOUNDS).observe(getViewLifecycleOwner(), measurements -> {
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