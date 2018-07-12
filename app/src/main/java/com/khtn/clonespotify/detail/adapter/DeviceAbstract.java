package com.khtn.clonespotify.detail.adapter;

import android.support.v7.widget.RecyclerView;

import com.khtn.clonespotify.model.Device;
import com.khtn.clonespotify.model.Video;

import java.util.List;

public abstract class DeviceAbstract extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public List<Device> devices;

    public abstract List<Device> getDevices();

    public abstract void setDevices(List<Device> devices);
}