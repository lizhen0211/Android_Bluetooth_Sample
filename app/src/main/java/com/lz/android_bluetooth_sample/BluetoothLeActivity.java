package com.lz.android_bluetooth_sample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class BluetoothLeActivity extends AppCompatActivity {

    public static final String TAG = BluetoothLeActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_le);
    }

    public void onStartScanClick(View view) {
        boolean isEnabled = BluetoothUtil.isEnabled(this);
        if (isEnabled) {
            BluetoothManager.getInstance(this).startScan(scanCallback);
        } else {
            //开启蓝牙
            boolean enable = BluetoothUtil.enable(this);
            if (!enable) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

    }

    public void onStopScanClick(View view) {
        BluetoothManager.getInstance(this).stopScan(scanCallback);
    }

    public void onSimulationAdvertiseClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //resultCode 0：拒绝 -1:允许
        if (requestCode == REQUEST_ENABLE_BT && resultCode == -1) {
            //do nothing
        } else {
            Toast.makeText(this, "开启蓝牙才能使用", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (!TextUtils.isEmpty(result.getScanRecord().getDeviceName())) {
                BluetoothDevice device = result.getDevice();
                //打印
                printScanRecord(result, device);
                //解析数据(只是把代码从scanrecord拿出来,方便调试)
                //ScanRecordParser.parseFromBytes(result.getScanRecord().getBytes());
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e(TAG, "errorCode:" + String.valueOf(errorCode));
        }
    };

    private void printScanRecord(ScanResult result, BluetoothDevice device) {
        Log.e("ScanResult", "==============begin==============");
        Log.e("device", device.getAddress() + ":" + device.getName() + ":" + device.getUuids() + ":" + result.getRssi());
        String scanRecordString = getScanRecordString(result);
        Log.e("scanRecord", scanRecordString);
        result.getScanRecord().getAdvertiseFlags();//0x01
        result.getScanRecord().getManufacturerSpecificData();//0xFF
        result.getScanRecord().getDeviceName();//0x09
        Log.e("ScanResult", "==============end==============");
    }

    private String getScanRecordString(ScanResult result) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = result.getScanRecord().getBytes();
        sb.append('[');
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
            sb.append(',');
        }
        sb.append(']');
        return sb.toString();
    }

}