package com.khtn.clonespotify.detail.abstracts;

import android.support.v7.widget.RecyclerView;

import com.khtn.clonespotify.model.Video;

import java.util.List;

public abstract class VideoAbstract extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public List<Video> videos;

    public abstract List<Video> getVideos();

    public abstract void setVideos(List<Video> videos);
}

