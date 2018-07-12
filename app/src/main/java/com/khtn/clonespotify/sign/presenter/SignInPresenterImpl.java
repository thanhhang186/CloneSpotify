package com.khtn.clonespotify.sign.presenter;

import android.app.Activity;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.model.Device;
import com.khtn.clonespotify.model.User;
import com.khtn.clonespotify.sign.view.SignInView;
import com.khtn.clonespotify.utils.Constants;
import com.khtn.clonespotify.utils.DeviceName;
import com.khtn.clonespotify.utils.PrefUtils;

public class SignInPresenterImpl implements SignInPresenter {
    private FirebaseAuth auth;
    private SignInView signInView;
    private Activity context;
    private static final String TAG = "SignInPre";

    public SignInPresenterImpl(Activity context, FirebaseAuth auth) {
        this.auth = auth;
        this.context = context;
    }

    @Override
    public void signIn(String email, String password) {
        signInView.setProgressVisibility(true);
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            signInView.showVadidationError();
        } else {
            FirebaseManager.getInstance().signInUser(context, signInView, email, password);
            addDevice();
        }

    }

    private void addDevice() {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Device device = new Device(android_id, DeviceName.getDeviceName());
        Log.i(TAG, "device_id: " + android_id);
        Log.i(TAG, "device_name: " + DeviceName.getDeviceName());
        Log.i(TAG, "user_id: " + PrefUtils.getUserId(context));
        FirebaseManager.getInstance().addDevice(PrefUtils.getUserId(context), device);
    }

    @Override
    public void checkSignIn() {

    }

    @Override
    public void attachView(SignInView view) {
        signInView = view;
    }

    @Override
    public void detachView() {
        signInView = null;
    }
}
