package com.khtn.clonespotify.detail.presenter;

import com.khtn.clonespotify.model.Video;

public interface VideoPresenter {
    void syncPause();
    void syncPlay();
    void getVideo();
    void setVideoCurrent(Video video);
}
