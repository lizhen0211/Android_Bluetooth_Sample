package com.lz.android_bluetooth_sample;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
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

    private List<BluetoothDevice> bluetoothDevices;

    public BluetoothRecyclerViewAdapter(List<BluetoothDevice> bluetoothDevices) {
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
        Log.e("+++---",bluetoothDevices.get(position).getName());
        holder.deviceNameTextView.setText(bluetoothDevices.get(position).getName());
        holder.bluetoothAddressTextView.setText(bluetoothDevices.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return this.bluetoothDevices.size();
    }

    public List<BluetoothDevice> getBluetoothDevices() {
        return bluetoothDevices;
    }

    public void setBluetoothDevices(List<BluetoothDevice> bluetoothDevices) {
        this.bluetoothDevices = bluetoothDevices;
    }

    public void addBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevices.add(bluetoothDevice);
    }

    public boolean containsDevice(BluetoothDevice bluetoothDevice) {
        boolean containsDevice = false;
        for (BluetoothDevice device : bluetoothDevices) {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceNameTextView = itemView.findViewById(R.id.device_name);
            bluetoothAddressTextView = itemView.findViewById(R.id.bluetooth_address);
        }
    }
}
