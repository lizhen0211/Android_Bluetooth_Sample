package com.lz.permission;

/**
 * 权限接口
 *
 * @author lizhen
 * @date 2018/8/13
 */
public interface IPermission {

    /**
     * 权限已被授予
     */
    void onPermissionsGranted();

    /**
     * SDK < 23
     */
    void onLessThanMarshmallow();

    /**
     * 请求权限后，被用户授予权限
     *
     * @param requestCode
     * @param perms
     */
    void onPermissionsGrantedAfterReq(int requestCode, String[] perms);

    /**
     * 请求权限后，被用户拒绝
     *
     * @param requestCode
     * @param perms
     */
    void onPermissionsDeniedAfterReq(int requestCode, String[] perms);

    /**
     * 请求权限后，被用户禁止并不在询问
     *
     * @param requestCode
     * @param perms
     */
    void onPermissionsDeniedAfterReqNoLongerAsk(int requestCode, String[] perms);
}
