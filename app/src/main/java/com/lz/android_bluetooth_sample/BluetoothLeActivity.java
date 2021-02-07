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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BluetoothLeActivity extends AppCompatActivity {

    public static final String TAG = BluetoothLeActivity.class.getSimpleName();

    public static final int REQUEST_ENABLE_BT = 1;

    private RecyclerView recyclerView;

    private BluetoothRecyclerViewAdapter adapter;

    private Map<String, Device> bluetoothDeviceMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_le);
        recyclerView = findViewById(R.id.bluetooth_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BluetoothRecyclerViewAdapter(DeviceUtil.convertMapToList(bluetoothDeviceMap));
        recyclerView.setAdapter(adapter);
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
                Log.e("+++", result.getScanRecord().getDeviceName() + ":::" + device.getName());
                Device device1 = DeviceUtil.convertDevice(device, result.getScanRecord().getDeviceName(), result.getRssi());

                bluetoothDeviceMap.put(device.getAddress(), device1);
                //数据转换为list
                List<Device> list = DeviceUtil.convertMapToList(bluetoothDeviceMap);
                //按照rssi排序
                DeviceUtil.sortByRssi(list);
                //标记在线
                DeviceUtil.markOnline(list);

                adapter.setBluetoothDevices(list);
                adapter.notifyDataSetChanged();

//                //打印
//                printScanRecord(result, device);
//                //解析数据(只是把代码从scanrecord拿出来,方便调试)
//                ScanRecordParser.parseFromBytes(result.getScanRecord().getBytes());
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
        Log.e("AdvertiseFlags", String.valueOf(result.getScanRecord().getAdvertiseFlags()));//0x01
        Log.e("ManufacturerSpecific", result.getScanRecord().getManufacturerSpecificData().toString());//0xFF
        Log.e("DeviceName", result.getScanRecord().getDeviceName());//0x09
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