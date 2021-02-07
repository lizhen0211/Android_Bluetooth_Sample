package com.lz.android_bluetooth_sample;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lizhen
 * @date 21-2-7
 */
public class DeviceUtil {

    public static Device convertDevice(BluetoothDevice bluetoothDevice, String deviceName, int rssi) {
        Device device = new Device();
//        device.setName(bluetoothDevice.getName());这个值 有时为null
        device.setName(deviceName);
        device.setAddress(bluetoothDevice.getAddress());
        device.setRssi(rssi);
        device.setScanTimestamp(new Date().getTime());
        return device;
    }

    public static List<Device> convertMapToList(Map<String, Device> map) {
        List<Device> list = new ArrayList<>();
        for (Map.Entry<String, Device> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    public static void sortByRssi(List<Device> deviceList) {
        Collections.sort(deviceList, new Comparator<Device>() {
            @Override
            public int compare(Device o1, Device o2) {
                return o2.getRssi() - o1.getRssi();
            }
        });
    }

    public static void markOnline(List<Device> deviceList) {
        for (Device device : deviceList) {
            long detaTime = new Date().getTime() - device.getScanTimestamp();
            if (detaTime > 5 * 1000) {
                device.setOnline(false);
            } else {
                device.setOnline(true);
            }
        }
    }
}
