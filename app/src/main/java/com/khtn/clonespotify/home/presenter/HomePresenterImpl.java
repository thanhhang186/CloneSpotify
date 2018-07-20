package com.khtn.clonespotify.home.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.home.view.HomeView;
import com.khtn.clonespotify.utils.PrefUtils;

public class HomePresenterImpl implements HomePresenter{
    private HomeView homeView;
    private Activity context;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public HomePresenterImpl(Activity context, FirebaseAuth auth) {
        this.context = context;
        this.auth = auth;
    }

    @Override
    public void displayVideos() {
        if(isViewAttached()){
            homeView.showLoadingProgress();
            //get data successfull -> hide progress
            homeView.displayHome();

        }
    }

    @Override
    public void setView(HomeView homeView) {
        this.homeView = homeView;
    }

    private boolean isViewAttached() {
        return homeView != null;
    }
}
