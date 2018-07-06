package com.khtn.clonespotify.home.view;

import com.khtn.clonespotify.model.Video;

public interface HomeView{
    void displayHome();
    void onLoadingFail(String errorMessage);
    void showLoadingProgress();
    void hideLoadingProgress();
    void onVideoClicked(Video video);
}
