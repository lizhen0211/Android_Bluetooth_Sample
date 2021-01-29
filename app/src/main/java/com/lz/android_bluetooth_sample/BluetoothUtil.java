package com.lz.android_bluetooth_sample;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author lizhen
 * @date 21-1-28
 */
public class BluetoothUtil {

    public static boolean isEnabled(Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothManager.getInstance(context).getBluetoothAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public static boolean enable(Context context) {
        BluetoothAdapter bluetoothAdapter = BluetoothManager.getInstance(context).getBluetoothAdapter();
        return bluetoothAdapter.enable();// 某些设备无法打开蓝牙
    }

    /**
     * 设备是否支持 BLE
     *
     * @param context
     * @return
     */
    public static boolean hasFeatureBluetoothLe(Context context) {
        return context.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }
}
