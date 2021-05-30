package com.example.myhomeapplication.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhomeapplication.R;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<DeviceItem> deviceItems;
    private OnDeviceItemClickedListener onDeviceItemClickedListener;

    public DeviceAdapter(ArrayList<DeviceItem> deviceItems, OnDeviceItemClickedListener onDeviceItemClickedListener) {
        this.deviceItems = deviceItems;
        this.onDeviceItemClickedListener = onDeviceItemClickedListener;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.device_item, parent, false);
        return new ViewHolder(view, onDeviceItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {

        holder.id.setText(deviceItems.get(position).getID());
        holder.name.setText(deviceItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return deviceItems.size();
    }

    public void updateAdapter(ArrayList<DeviceItem> deviceItems){
        this.deviceItems = deviceItems;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView id;
        TextView name;
        OnDeviceItemClickedListener onDeviceItemClickedListener;

        public ViewHolder(@NonNull View itemView, OnDeviceItemClickedListener onDeviceItemClickedListener) {
            super(itemView);
            id = itemView.findViewById(R.id.recycler_view_deviceid);
            name = itemView.findViewById(R.id.recycler_view_device_name);
            this.onDeviceItemClickedListener = onDeviceItemClickedListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String s = deviceItems.get(getAdapterPosition()).getID();
            onDeviceItemClickedListener.onItemClicked(s);

        }
    }

    public interface OnDeviceItemClickedListener {
        void onItemClicked(String id);
    }
}
