package com.khtn.clonespotify.model;

import java.io.Serializable;

public class Video implements Serializable {
    private String videoId;
    private String deviceId;
    private String url;
    private String nameSong;
    private String author;
    private boolean isLike;
    private long totalTime;
    private String describe;
    private boolean isPause;
    private boolean isPlay;
    private int time;
    private long totalView;

    public long getTotalView() {
        return totalView;
    }

    public void setTotalView(long totalView) {
        this.totalView = totalView;
    }

    public Video(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDeviceId() {

        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isIsPlay() {
        return isPlay;
    }

    public void setIsPlay(boolean play) {
        isPlay = play;
    }

    private boolean isRepeat;
    private String description;
    private int resolution;

    public Video() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean like) {
        isLike = like;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isIsPause() {
        return isPause;
    }

    public void setIsPause(boolean pause) {
        isPause = pause;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
}
