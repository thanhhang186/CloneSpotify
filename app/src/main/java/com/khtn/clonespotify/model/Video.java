package com.khtn.clonespotify.model;

import java.io.Serializable;

public class Video implements Serializable {
    private String videoId;
    private String url;
    private String nameSong;
    private String author;
    private boolean isLike;
    private long totalTime;
    private boolean isPause;
    private boolean isPlay;

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
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

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public boolean isPause() {
        return isPause;
    }

    public void setPause(boolean pause) {
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
