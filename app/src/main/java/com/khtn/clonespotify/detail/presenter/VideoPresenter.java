package com.khtn.clonespotify.detail.presenter;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.database.ValueEventListener;
import com.khtn.clonespotify.model.Video;

public interface VideoPresenter {
    void syncPause();
    void syncPlay();
    void getVideo();
    void setVideoCurrent(Video video);
    void getVideoCurrent(ValueEventListener valueEventListener);
}
