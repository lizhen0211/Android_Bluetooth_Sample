package com.lz.android_bluetooth_sample;

/**
 * @author lizhen
 * @date 21-2-7
 */
public class Device {
    private String name;
    private String address;
    private int rssi;
    private long scanTimestamp;
    private boolean online;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public long getScanTimestamp() {
        return scanTimestamp;
    }

    public void setScanTimestamp(long scanTimestamp) {
        this.scanTimestamp = scanTimestamp;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
