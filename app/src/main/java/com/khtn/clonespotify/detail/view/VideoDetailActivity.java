package com.khtn.clonespotify.detail.view;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.khtn.clonespotify.R;
import com.khtn.clonespotify.model.Video;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoDetailActivity extends AppCompatActivity implements VideoDetailFragment.Callback {
    @BindView(R.id.container_detail)
    FrameLayout container;
    public static final String VIDEO_DETAIL_FRAGMENT = "VideoDeatilFragment";
    public static final String DEVICE_FRAGMENT = "DeviceFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        loadVideoDetailFragment();
    }

    private void loadVideoDetailFragment() {
        VideoDetailFragment videoDetailFragment = new VideoDetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_detail,videoDetailFragment,VIDEO_DETAIL_FRAGMENT).commit();
    }

    @Override
    public void onClickVideoRecommend(Video video) {
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra("video",video);
        startActivity(intent);
    }

    @Override
    public void loadDeviceFragment() {
        DeviceFragment deviceFragment = new DeviceFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_detail,deviceFragment,DEVICE_FRAGMENT).commit();
    }

    @Override
    public void doBack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


}
