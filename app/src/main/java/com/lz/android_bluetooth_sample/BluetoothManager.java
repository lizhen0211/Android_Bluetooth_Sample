package com.lz.android_bluetooth_sample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.content.Context;

/**
 * @author lizhen
 * @date 21-1-28
 */
public class BluetoothManager {

    private android.bluetooth.BluetoothManager bluetoothManager;

    private static BluetoothManager instance;

    private BluetoothManager(Context context) {
        //获取实例时，会有阻塞。所以使用单例,全局使用一个
        bluetoothManager = (android.bluetooth.BluetoothManager) context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
    }

    public static BluetoothManager getInstance(Context context) {
        if (instance == null) {
            instance = new BluetoothManager(context);
        }
        return instance;
    }

    /**
     * 获取蓝牙适配器
     *
     * @return
     */
    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothManager.getAdapter();
    }

    /**
     * 开始扫描
     *
     * @param callback
     */
    public void startScan(ScanCallback callback) {
        getBluetoothAdapter().getBluetoothLeScanner().startScan(callback);
    }

    /**
     * 结束扫描
     *
     * @param callback
     */
    public void stopScan(ScanCallback callback) {
        getBluetoothAdapter().getBluetoothLeScanner().stopScan(callback);
    }
}
