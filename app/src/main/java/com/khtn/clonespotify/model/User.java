package com.khtn.clonespotify.model;

import java.util.List;

public class User {
    private String uid;
    private String id;
    private String email;
    private List<Device> devices;
    private List<Video> favorVideoList;
    private List<Video> recommderVideo;
    private String currentVideoID;
    private String deviceControlID;

    public String getDeviceControlID() {
        return deviceControlID;
    }

    public void setDeviceControlID(String deviceControlID) {
        this.deviceControlID = deviceControlID;
    }

    public String getCurrentVideoID() {
        return currentVideoID;
    }

    public void setCurrentVideoID(String currentVideoID) {
        this.currentVideoID = currentVideoID;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public User(String id, String uid, String email) {
        this.uid = uid;
        this.id = id;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Video> getFavorVideoList() {
        return favorVideoList;
    }

    public void setFavorVideoList(List<Video> favorVideoList) {
        this.favorVideoList = favorVideoList;
    }

    public List<Video> getRecommderVideo() {
        return recommderVideo;
    }

    public void setRecommderVideo(List<Video> recommderVideo) {
        this.recommderVideo = recommderVideo;
    }
}
