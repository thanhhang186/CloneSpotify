package com.khtn.clonespotify.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.khtn.clonespotify.R;
import com.khtn.clonespotify.detail.view.VideoDetailActivity;
import com.khtn.clonespotify.model.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements VideoTopListFragment.Callback{
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    @BindView(R.id.container_home)
    FrameLayout container_home;
    @BindView(R.id.btn_setting)
    ImageView btn_setting;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    public static final String SETTING_FRAGMENT= "SettingFragment";
    public static final String VIDEO_TOP_LIST_FRAGMENT = "VideoTopListFragment";
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
                switch (item.getItemId()){
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
        initLayout();
        btn_setting.setOnClickListener(v->{
            showSettingFragment();
        });
    }

    private void initLayout() {
        if(true){
            loadVideoTopListFragment();
        } else {
            LayoutInflater inflater = (LayoutInflater)getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.layout_disconnected, null);
            container_home.removeAllViews();
            container_home.addView(v);
        }
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
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra("video",video);
        startActivity(intent);
    }

}
