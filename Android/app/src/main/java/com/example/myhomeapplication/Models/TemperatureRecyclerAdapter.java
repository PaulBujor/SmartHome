package com.example.myhomeapplication.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhomeapplication.R;

import java.util.List;

public class TemperatureRecyclerAdapter extends RecyclerView.Adapter<TemperatureRecyclerAdapter.ViewHolder> {
    LiveData<List<Measurement>> measurements;
    public TemperatureRecyclerAdapter(LiveData<List<Measurement>> measurements){
        this.measurements =  measurements;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_temperatures, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Measurement measurement = measurements.getValue().get(position);
        holder.id.setText((int) measurement.getMeasurementID());
        holder.timestamp.setText((CharSequence) measurement.getTimestamp());
        holder.value.setText((int) measurement.getValue());

    }


    @Override
    public int getItemCount() {
        return measurements.getValue().size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
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
