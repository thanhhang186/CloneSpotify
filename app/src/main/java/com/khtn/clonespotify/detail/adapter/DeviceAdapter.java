package com.khtn.clonespotify.detail.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.detail.view.OnClickDeviceListener;
import com.khtn.clonespotify.model.Device;
import com.khtn.clonespotify.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter extends DeviceAbstract  {
    private Context context;
    private List<Device> devices;
    private OnClickDeviceListener onClickDeviceListener;

    public DeviceAdapter(List<Device> devices) {
        this.devices = devices;
    }
    public DeviceAdapter(OnClickDeviceListener onClickDeviceListener, List<Device> devices) {
        this.onClickDeviceListener = onClickDeviceListener;
        this.devices = devices;
    }

    @Override
    public List<Device> getDevices() {
        return this.devices;
    }

    @Override
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name_device)
        TextView nameDevice;
        @BindView(R.id.img_device)
        ImageView imgDevice;
        public DeviceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void bind(Device device){
            if(device.getDeviceID().equals(PrefUtils.getDeviceControlId(context))){
                imgDevice.setImageResource(R.drawable.smartphone_active);
                nameDevice.setText(device.getDeviceName());
                nameDevice.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
            } else {
                nameDevice.setText(device.getDeviceName());
                imgDevice.setImageResource(R.drawable.smartphone);
                nameDevice.setOnClickListener(v->{
                    createAlertDialog(context, device);
                });
            }

        }

        private void createAlertDialog(Context context, Device device) {
            new AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage("Would you like to listen to music on device " + device.getDeviceName() )
                    .setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> {
                    })
                    .setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
                        FirebaseManager.getInstance().setDeviceControlID(PrefUtils.getUserId(context), device.getDeviceID());

                    })
                    .show();
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;
        View view = layoutInflater.inflate(R.layout.device_item,parent,false);
        viewHolder = new DeviceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Device device = devices.get(position);
        ((DeviceViewHolder)holder).bind(device);
    }

    @Override
    public int getItemCount() {
        return (devices == null)? 0: devices.size();
    }
}
