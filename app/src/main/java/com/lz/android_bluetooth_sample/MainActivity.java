package com.lz.android_bluetooth_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBaseBluetoothClick(View view) {

    }

    public void onBluetoothBleClick(View view) {
        Intent intent = new Intent(this, BluetoothLeActivity.class);
        startActivity(intent);
    }
}