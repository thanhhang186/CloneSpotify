package com.khtn.clonespotify.detail.presenter;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.detail.adapter.DeviceAbstract;
import com.khtn.clonespotify.utils.PrefUtils;

public class DevicePresenterImpl implements DevicePresenter {
    public static final String TAG = "DevicePre";
    private Activity context;
    private FirebaseAuth auth;

    public DevicePresenterImpl(Activity context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }


    @Override
    public void getAllDevices(DeviceAbstract adapter) {
        Log.i(TAG, "size return: " );
        FirebaseManager.getInstance().getDeviceListByID(PrefUtils.getUserId(context), adapter);
    }


}
