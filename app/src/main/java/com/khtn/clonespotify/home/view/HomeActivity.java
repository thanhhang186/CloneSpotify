package com.khtn.clonespotify.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.detail.presenter.VideoPresenter;
import com.khtn.clonespotify.detail.presenter.VideoPresenterImpl;
import com.khtn.clonespotify.detail.view.VideoDetailActivity;
import com.khtn.clonespotify.home.interfaces.OnBackPressedListener;
import com.khtn.clonespotify.model.Video;
import com.khtn.clonespotify.utils.Constants;
import com.khtn.clonespotify.utils.PrefUtils;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements VideoTopListFragment.Callback, YouTubePlayer.OnInitializedListener, OnBackPressedListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.container_home)
    FrameLayout container_home;
    @BindView(R.id.btn_setting)
    ImageView btn_setting;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;

    public static final String SETTING_FRAGMENT = "SettingFragment";
    public static final String VIDEO_TOP_LIST_FRAGMENT = "VideoTopListFragment";
    private VideoPresenter videoPresenter;
    private FirebaseAuth auth;
    private YouTubePlayerSupportFragment youtubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        toolbar.setVisibility(View.VISIBLE);
                        loadHomeFragment();
                        break;
                    case R.id.navigation_search:
                        loadSearchFragment();
                        break;
                    case R.id.navigation_user:
                        loadProfileFragment();
                        break;
                }
                return false;
            }
        });
    }

    private void loadProfileFragment() {
        Toast.makeText(this, "Profile Fragment", Toast.LENGTH_SHORT).show();
    }

    private void loadSearchFragment() {
        Toast.makeText(this, "Search Fragment", Toast.LENGTH_SHORT).show();
    }

    private void loadHomeFragment() {
        initLayout();
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        videoPresenter = new VideoPresenterImpl(this, auth);
        initLayout();
        btn_setting.setOnClickListener(v -> {
            showSettingFragment();
        });

    }

    private void initLayout() {
        if (true) {
            loadVideoTopListFragment();
        } else {
            LayoutInflater inflater = (LayoutInflater) getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.layout_disconnected, null);
            container_home.removeAllViews();
            container_home.addView(v);
        }
        Log.i(TAG, "control DV: " + PrefUtils.getDeviceControlId(this));
        Log.i(TAG, "current DV: " + PrefUtils.getDeviceCurrentlId(this));
        youtubeView = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view);
        youtubeView.initialize(Constants.YOUTUBE_API_KEY, this);
    }

    private void showSettingFragment() {
        toolbar.setVisibility(View.GONE);
        SettingFragment settingFragment = new SettingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_home, settingFragment, SETTING_FRAGMENT).commit();
    }

    private void loadVideoTopListFragment() {
        VideoTopListFragment videoTopListFragment = new VideoTopListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_home, videoTopListFragment, VIDEO_TOP_LIST_FRAGMENT).commit();
    }

    @Override
    public void onVideoClicked(Video video) {
        Log.d(TAG, video.getTotalView() + "");
        FirebaseManager.getInstance().setDeviceControlID(PrefUtils.getUserId(this), PrefUtils.getDeviceCurrentlId(this));
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Log.i(TAG, "onInitializationSuccess");
        if(!PrefUtils.getDeviceCurrentlId(this).equals(PrefUtils.getDeviceControlId(this))){
            videoPresenter.getVideoCurrent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        youtubeView.getView().setVisibility(View.VISIBLE);
                        Video video = dataSnapshot.getValue(Video.class);
                        Log.i(TAG, "name_video: "+video.getNameSong());
                        youTubePlayer.loadVideo(video.getUrl());
                        //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                        youTubePlayer.seekToMillis(video.getTime());
                        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                            @Override
                            public void onLoading() {
                            }

                            @Override
                            public void onLoaded(String s) {
                                Log.i(TAG, "onLoaded");
                            }

                            @Override
                            public void onAdStarted() {
                                Log.i(TAG, "onAdStarted");
                            }

                            @Override
                            public void onVideoStarted() {
                                Log.i(TAG, "onVideoStartec");
                                if (video.isIsPause()) {
                                    youTubePlayer.seekToMillis(video.getTime());
                                    video.setTime(youTubePlayer.getCurrentTimeMillis());
                                    videoPresenter.setVideoCurrent(video);
                                    youTubePlayer.pause();
                                }
                                if (video.isIsPlay()) {
                                    youTubePlayer.seekToMillis(video.getTime());
                                    video.setTime(youTubePlayer.getCurrentTimeMillis());
                                    videoPresenter.setVideoCurrent(video);
                                    youTubePlayer.play();
                                }
                            }

                            @Override
                            public void onVideoEnded() {
                                Log.i(TAG, "onVideoEnded");
                            }

                            @Override
                            public void onError(YouTubePlayer.ErrorReason errorReason) {
                                Log.i(TAG, "onError: "+ errorReason.toString());
                            }
                        });

                    } else {
                        youtubeView.getView().setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error Intializing Youtube Player", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void doBack() {

    }
}
