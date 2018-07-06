package com.khtn.clonespotify.detail.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.detail.adapter.VideoDetailAdapter;
import com.khtn.clonespotify.home.interfaces.OnBackPressedListener;
import com.khtn.clonespotify.model.Video;
import com.khtn.clonespotify.utils.Contants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailFragment extends Fragment implements YouTubePlayer.OnInitializedListener, OnBackPressedListener {
    @BindView(R.id.videos_recommend)
    RecyclerView videoRecommendList;
    private YouTubePlayerSupportFragment youtubeView;
    private Callback callback;
    private List<Video> videos;
    private VideoDetailAdapter videoDetailAdapter;
    @BindView(R.id.tv_devices)
    TextView tvDevices;

    @OnClick(R.id.tv_devices)
    void onClick() {
        loadDeviceFragment();
    }

    public VideoDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        ButterKnife.bind(this, view);
        initYoutubePlayer();
        initData();
        return view;
    }

    private void initData() {
        videos = new ArrayList<>();
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videoDetailAdapter = new VideoDetailAdapter(videos);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        videoRecommendList.setLayoutManager(linearLayoutManager);
        videoRecommendList.setHasFixedSize(true);
        videoRecommendList.setItemAnimator(new DefaultItemAnimator());
        videoRecommendList.setAdapter(videoDetailAdapter);
    }

    private void initYoutubePlayer() {
        youtubeView = (YouTubePlayerSupportFragment) getChildFragmentManager().findFragmentById(R.id.youtube_view);
        youtubeView.initialize(Contants.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (VideoDetailFragment.Callback) context;
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo("gUsB57zR52c");
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {
                youTubePlayer.pause();
            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void doBack() {
        callback.doBack();
    }

    public interface Callback {
        void onClickVideoRecommend(Video video);
        void loadDeviceFragment();

        void doBack();
    }
    private void loadDeviceFragment() {
        callback.loadDeviceFragment();
    }
}
