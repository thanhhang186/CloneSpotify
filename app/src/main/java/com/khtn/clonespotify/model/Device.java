package com.khtn.clonespotify.model;

public class Device {
    private String deviceID;
    private String deviceName;
    private boolean isActive = false;

    public Device(String deviceID, String deviceName) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
    }

    public Device(String deviceID, String deviceName, boolean isActive) {
        this.deviceID = deviceID;
        this.deviceName = deviceName;
        this.isActive = isActive;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Device() {
    }

    public Device(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
