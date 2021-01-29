package com.lz.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限请求工具类
 *
 * @author lizhen
 * @date 2018/8/13
 */
public class PermissionsUtil {

    /**
     * 请求权限
     *
     * @param activity
     * @param permissions
     * @param reqCode
     * @param iPermission
     */
    public static void requestPermission(Activity activity, String[] permissions, int reqCode, IPermission iPermission) {
        if (PermissionsUtil.isSupportRequestPermission()) {
            List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
            if (deniedPermissions.size() > 0) {
                //请求权限
                ActivityCompat.requestPermissions(activity,
                        deniedPermissions.toArray(
                                new String[deniedPermissions.size()]),
                        reqCode);
                //返回结果 onRequestPermissionsResult
            } else {
                if (iPermission != null) {
                    iPermission.onPermissionsGranted();
                }
            }
        } else {
            if (iPermission != null) {
                iPermission.onLessThanMarshmallow();
            }
        }
    }

    /**
     * 权限结果处理
     *
     * @param activity
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param iPermission
     */
    public static void requestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, IPermission iPermission) {
        if (!verifyPermissions(grantResults)) {
            if (shouldShowRequestPermissionRationale(permissions, activity)) {
                if (iPermission != null) {
                    iPermission.onPermissionsDeniedAfterReqNoLongerAsk(requestCode, permissions);
                }
            } else {
                if (iPermission != null) {
                    iPermission.onPermissionsDeniedAfterReq(requestCode, permissions);
                }
            }
        } else {
            if (iPermission != null) {
                iPermission.onPermissionsGrantedAfterReq(requestCode, permissions);
            }
        }
    }

    /**
     * 获取权限描述
     *
     * @param perms
     * @return
     */
    public static String getDeniedPermissionDesc(String... perms) {
        StringBuilder needPerms = new StringBuilder();
        for (int i = 0; i < perms.length; ++i) {
            String perm = perms[i];
            // group:android.permission-group.STORAGE
            if (perm.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                needPerms.append("读写内存权限");
                // group:android.permission-group.CAMERA
            } else if (perm.equals(Manifest.permission.CAMERA)) {
                needPerms.append("相机权限");
                // group:android.permission-group.LOCATION
            } else if (perm.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                    || perm.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                needPerms.append("位置权限");
                // group:android.permission-group.SMS
            } else if (perm.equals(Manifest.permission.READ_SMS)
                    || perm.equals(Manifest.permission.RECEIVE_WAP_PUSH)
                    || perm.equals(Manifest.permission.RECEIVE_MMS)
                    || perm.equals(Manifest.permission.RECEIVE_SMS)
                    || perm.equals(Manifest.permission.SEND_SMS)) {
                needPerms.append("读取和发送短信权限");
                // group:android.permission-group.CALENDAR
            } else if (perm.equals(Manifest.permission.READ_CALENDAR)
                    || perm.equals(Manifest.permission.WRITE_CALENDAR)) {
                needPerms.append("读写日历权限");
                // group:android.permission-group.SENSORS
            } else if (perm.equals(Manifest.permission.BODY_SENSORS)) {
                needPerms.append("人体传感器权限");
                // group:android.permission-group.MICROPHONE
            } else if (perm.equals(Manifest.permission.RECORD_AUDIO)) {
                needPerms.append("录音权限");
                // group:android.permission-group.CONTACTS
            } else if (perm.equals(Manifest.permission.WRITE_CONTACTS)
                    || perm.equals(Manifest.permission.GET_ACCOUNTS)
                    || perm.equals(Manifest.permission.READ_CONTACTS)) {
                needPerms.append("读写通讯录权限");
                // group:android.permission-group.PHONE
            } else if (perm.equals(Manifest.permission.READ_CALL_LOG)
                    || perm.equals(Manifest.permission.READ_PHONE_STATE)
                    || perm.equals(Manifest.permission.CALL_PHONE)
                    || perm.equals(Manifest.permission.WRITE_CALL_LOG)
                    || perm.equals(Manifest.permission.USE_SIP)
                    || perm.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)
                    || perm.equals(Manifest.permission.ADD_VOICEMAIL)) {
                needPerms.append("读取手机状态权限");
            }

            if (i < perms.length - 1) {
                needPerms.append("、");
            }
        }
        return needPerms.toString();
    }

    /**
     * 是否支持权限请求判断，6.0及以上支持申请权限
     *
     * @return
     */
    private static boolean isSupportRequestPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 检查权限组是否已允许
     *
     * @param permissions
     * @since 2.5.0
     */
    public static boolean checkPermissions(Activity activity, int reqCode, String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(activity, permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(activity,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    reqCode);
            return false;
        }
        return true;
    }

    /**
     * 获取权限需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private static List<String> findDeniedPermissions(Activity activity, String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(activity,
                    perm) != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 是否权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private static boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
     * 第一次打开App时	false；
     * 上次弹出权限点击了禁止（但没有勾选“下次不在询问”） true；
     * 上次选择禁止并勾选：下次不在询问	false
     *
     * @param deniedPermissions
     * @param activity
     * @return
     */
    private static boolean shouldShowRequestPermissionRationale(String[] deniedPermissions, Activity activity) {
        boolean flag = false;
        for (String permission : deniedPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

}
