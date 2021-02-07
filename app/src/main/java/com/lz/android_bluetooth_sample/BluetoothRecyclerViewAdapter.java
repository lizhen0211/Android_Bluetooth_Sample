package com.lz.android_bluetooth_sample;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author lizhen
 * @date 21-2-7
 */
public class BluetoothRecyclerViewAdapter extends RecyclerView.Adapter<BluetoothRecyclerViewAdapter.ViewHolder> {

    private List<Device> bluetoothDevices;

    public BluetoothRecyclerViewAdapter(List<Device> bluetoothDevices) {
        this.bluetoothDevices = bluetoothDevices;
    }

    @NonNull
    @Override
    public BluetoothRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_recycle_view_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothRecyclerViewAdapter.ViewHolder holder, int position) {
        Device bluetoothDevice = bluetoothDevices.get(position);
        holder.deviceNameTextView.setText(bluetoothDevice.getName());
        holder.bluetoothAddressTextView.setText(bluetoothDevice.getAddress());
        holder.rssiTextView.setText(String.valueOf(bluetoothDevice.getRssi()));
        if (bluetoothDevice.isOnline()) {
            holder.rssiTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.black));
        } else {
            holder.rssiTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));
        }
    }

    @Override
    public int getItemCount() {
        return this.bluetoothDevices.size();
    }

    public void setBluetoothDevices(List<Device> bluetoothDevices) {
        this.bluetoothDevices = bluetoothDevices;
    }

    public void addBluetoothDevice(Device bluetoothDevice) {
        this.bluetoothDevices.add(bluetoothDevice);
    }

    public boolean containsDevice(BluetoothDevice bluetoothDevice) {
        boolean containsDevice = false;
        for (Device device : bluetoothDevices) {
            if (device.getAddress().equals(bluetoothDevice.getAddress())) {
                containsDevice = true;
                break;
            }
        }
        return containsDevice;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView deviceNameTextView;

        public TextView bluetoothAddressTextView;

        public TextView rssiTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.device_name);
            bluetoothAddressTextView = itemView.findViewById(R.id.bluetooth_address);
            rssiTextView = itemView.findViewById(R.id.rssi_textview);
        }
    }
}
