package com.lz.android_bluetooth_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lz.permission.IPermission;
import com.lz.permission.PermissionGroup;
import com.lz.permission.PermissionsUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBaseBluetoothClick(View view) {

    }

    public void onBluetoothBleClick(View view) {
        //请求权限
        PermissionsUtil.requestPermission(MainActivity.this, PermissionGroup.LOCATION_PERMISSIONS, PermissionGroup.LOCATION_REQUEST_CODE, requestPermissionCallback);
    }

    private IPermission requestPermissionCallback = new IPermission() {
        @Override
        public void onPermissionsGranted() {
            Log.e(TAG, "onPermissionsGranted");
            Intent intent = new Intent(MainActivity.this, BluetoothLeActivity.class);
            startActivity(intent);
        }

        @Override
        public void onLessThanMarshmallow() {
            Log.e(TAG, "onLessThanMarshmallow");
            Intent intent = new Intent(MainActivity.this, BluetoothLeActivity.class);
            startActivity(intent);
        }

        @Override
        public void onPermissionsGrantedAfterReq(int requestCode, String[] perms) {
            Log.e(TAG, "onPermissionsGrantedAfterReq");
            Intent intent = new Intent(MainActivity.this, BluetoothLeActivity.class);
            startActivity(intent);
        }

        @Override
        public void onPermissionsDeniedAfterReq(int requestCode, String[] perms) {
            Log.e(TAG, "onPermissionsDeniedAfterReq");
            Toast.makeText(MainActivity.this, "onPermissionsDeniedAfterReq", Toast.LENGTH_LONG).show();
            SystemSettingUtil.openApplicationDetailSettingsForResult(MainActivity.this, PermissionGroup.LOCATION_REQUEST_CODE);
        }

        @Override
        public void onPermissionsDeniedAfterReqNoLongerAsk(int requestCode, String[] perms) {
            Log.e(TAG, "onPermissionsDeniedAfterReqNoLongerAsk");
            Toast.makeText(MainActivity.this, "onPermissionsDeniedAfterReqNoLongerAsk", Toast.LENGTH_LONG).show();
            SystemSettingUtil.openApplicationDetailSettingsForResult(MainActivity.this, PermissionGroup.LOCATION_REQUEST_CODE);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsUtil.requestPermissionsResult(this, PermissionGroup.LOCATION_REQUEST_CODE, permissions, grantResults, requestPermissionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (requestCode == PermissionGroup.LOCATION_REQUEST_CODE) {
            //请求必要权限
            PermissionsUtil.requestPermission(MainActivity.this, PermissionGroup.LOCATION_PERMISSIONS, PermissionGroup.LOCATION_REQUEST_CODE, requestPermissionCallback);
        }
    }
}