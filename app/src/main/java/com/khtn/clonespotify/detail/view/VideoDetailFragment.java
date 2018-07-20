package com.khtn.clonespotify.detail.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.database.FirebaseManager;
import com.khtn.clonespotify.detail.adapter.VideoDetailAdapter;
import com.khtn.clonespotify.detail.presenter.VideoPresenter;
import com.khtn.clonespotify.detail.presenter.VideoPresenterImpl;
import com.khtn.clonespotify.home.interfaces.OnBackPressedListener;
import com.khtn.clonespotify.model.Video;
import com.khtn.clonespotify.utils.Constants;
import com.khtn.clonespotify.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDetailFragment extends Fragment implements YouTubePlayer.OnInitializedListener, OnBackPressedListener {
    private static final String TAG = VideoDetailFragment.class.getSimpleName();
    @BindView(R.id.videos_recommend)
    RecyclerView videoRecommendList;
    private YouTubePlayerSupportFragment youtubeView;
    private Callback callback;
    private List<Video> videos;
    private VideoDetailAdapter videoDetailAdapter;
    private VideoPresenter videoPresenter;
    private FirebaseAuth auth;
    @BindView(R.id.tv_devices)
    TextView tvDevices;

    @OnClick(R.id.tv_devices)
    void onClick() {
        loadDeviceFragment();
    }

    private Video mVideo;

    public VideoDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        ButterKnife.bind(this, view);
        auth = FirebaseAuth.getInstance();
        videoPresenter = new VideoPresenterImpl(getActivity(), auth);
        Intent intent = getActivity().getIntent();
        mVideo = (Video) intent.getSerializableExtra("video");
        mVideo.setDeviceId(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        mVideo.setIsPause(false);
        mVideo.setIsPlay(false);
        FirebaseManager.getInstance().setDeviceControlID(PrefUtils.getUserId(getContext()), PrefUtils.getDeviceCurrentlId(getContext()));
        if(!PrefUtils.getDeviceControlId(getContext()).equals(PrefUtils.getDeviceCurrentlId(getContext()))){
            onStop();
        }
        Log.i(TAG, "Total View: " + mVideo.getTotalView());
        videoPresenter.setVideoCurrent(mVideo);
        initYoutubePlayer();
        initData();
        return view;
    }

    private void initData() {
        videos = new ArrayList<>();
        videos.add(mVideo);
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
        youtubeView.initialize(Constants.YOUTUBE_API_KEY, this);
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
        youTubePlayer.loadVideo(mVideo.getUrl());
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {
                mVideo.setTime(youTubePlayer.getCurrentTimeMillis());
                videoPresenter.setVideoCurrent(mVideo);
            }

            @Override
            public void onVideoEnded() {
                mVideo.setTime(youTubePlayer.getCurrentTimeMillis());
                videoPresenter.setVideoCurrent(mVideo);

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });
        youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {
                mVideo.setIsPause(false);
                mVideo.setIsPlay(true);
                mVideo.setTime(youTubePlayer.getCurrentTimeMillis());
                videoPresenter.setVideoCurrent(mVideo);
                mVideo.setDeviceId(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
                Log.i(TAG, "Video Playing: " + youTubePlayer.getCurrentTimeMillis());
            }

            @Override
            public void onPaused() {
                mVideo.setIsPause(true);
                mVideo.setIsPlay(false);
                mVideo.setTime(youTubePlayer.getCurrentTimeMillis());
                videoPresenter.setVideoCurrent(mVideo);
                mVideo.setDeviceId(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
                Log.i(TAG, "Video Pause: " + youTubePlayer.getCurrentTimeMillis());
            }

            @Override
            public void onStopped() {
                mVideo.setIsPause(false);
                mVideo.setIsPlay(true);
                mVideo.setTime(youTubePlayer.getCurrentTimeMillis());
                videoPresenter.setVideoCurrent(mVideo);
                mVideo.setDeviceId(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
                Log.i(TAG, "Video Stop: " + youTubePlayer.getCurrentTimeMillis());

            }

            @Override
            public void onBuffering(boolean b) {
                mVideo.setTime(youTubePlayer.getCurrentTimeMillis());
                videoPresenter.setVideoCurrent(mVideo);
            }

            @Override
            public void onSeekTo(int i) {
                mVideo.setTime(i);
                mVideo.setDeviceId(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
                videoPresenter.setVideoCurrent(mVideo);
                Log.i(TAG, "Video onSeekTo: " + i);
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

    @Override
    public void onStop() {
        super.onStop();
    }
}
