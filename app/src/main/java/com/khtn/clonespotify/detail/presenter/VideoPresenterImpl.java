package com.khtn.clonespotify.detail.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.model.Video;
import com.khtn.clonespotify.utils.PrefUtils;

public class VideoPresenterImpl implements VideoPresenter {
    private Activity context;
    private FirebaseAuth auth;

    public VideoPresenterImpl(Activity context, FirebaseAuth auth) {
        this.auth = auth;
        this.context = context;
    }

    @Override
    public void syncPause() {

    }

    @Override
    public void syncPlay() {

    }

    @Override
    public void getVideo() {

    }

    @Override
    public void setVideoCurrent(Video video) {
        FirebaseManager.getInstance().setVideoCurrent(PrefUtils.getUserId(context), video);
    }
}
