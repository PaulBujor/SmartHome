package com.example.myhomeapplication.Models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhomeapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TemperatureRecyclerAdapter extends RecyclerView.Adapter<TemperatureRecyclerAdapter.ViewHolder> {

    private List<Measurement> measurements;

    public TemperatureRecyclerAdapter(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_temperatures, parent, false); //make sure attachToRoot is false to keep ConstraintLayout from shrinking
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Measurement measurement = measurements.get(position);
        holder.id.setText(String.valueOf(measurement.getMeasurementID()));
        Date toFormat = measurement.getTimestamp();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.applyPattern("yyyy-MM-dd HH:mm");
        String newDate = sdf.format(toFormat);

        holder.timestamp.setText(newDate);
        Log.d("Adapter debug", "" + measurement.getValue());
        holder.value.setText(String.valueOf(measurement.getValue()));

    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView timestamp;
        TextView value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.prefix_temperature);
            this.timestamp = itemView.findViewById(R.id.temperatureTimeStamp);
            this.value = itemView.findViewById(R.id.temperatureValue);
        }
    }
}
