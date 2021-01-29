package com.lz.android_bluetooth_sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author lizhen
 * @date 21-1-29
 */
public class SystemSettingUtil {
    /**
     * 打开应用详情页
     *
     * @param activity
     */
    public static void openApplicationDetailSettingsForResult(Activity activity, int reqCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivityForResult(intent, reqCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
