package com.khtn.clonespotify.home.adapter;

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

import com.khtn.clonespotify.R;
import com.khtn.clonespotify.model.Device;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Device> devices;

    public DeviceAdapter(List<Device> devices) {
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
            if(true){
                imgDevice.setImageResource(R.drawable.smartphone_active);
                nameDevice.setText("HTC Desire 728g");
                nameDevice.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
            } else {
                imgDevice.setImageResource(R.drawable.smartphone);
            }
            nameDevice.setOnClickListener(v->{
                showPopupConfirmDevice();
            });
        }

        private void showPopupConfirmDevice() {
            createAlertDialog(context, "\"HTC Desire 728g\"");
        }
        private void createAlertDialog(Context context, String nameDevice) {
            new AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage("Would you like to listen to music on device " + nameDevice)
                    .setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> {
                    })
                    .setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
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
