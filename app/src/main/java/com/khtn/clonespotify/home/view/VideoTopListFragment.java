package com.khtn.clonespotify.home.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.khtn.clonespotify.R;
import com.khtn.clonespotify.home.adapter.VideoAdapter;
import com.khtn.clonespotify.home.presenter.HomePresenterImpl;
import com.khtn.clonespotify.model.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoTopListFragment extends Fragment implements HomeView{
    @BindView(R.id.video_top_list)
    RecyclerView videoTopList;
    private VideoAdapter videoAdapter;
    private List<Video> videos;
    private Callback callback;
        private HomePresenterImpl homePresenter;
    private FirebaseAuth auth;
    public VideoTopListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_top_list, container, false);
        ButterKnife.bind(this,view);
        auth = FirebaseAuth.getInstance();
        homePresenter = new HomePresenterImpl(getActivity(), auth);
        homePresenter.setView(this);
        homePresenter.displayVideos();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initVideoTopList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        videoTopList.setLayoutManager(layoutManager);
        videoTopList.setHasFixedSize(true);
        videoTopList.setItemAnimator(new DefaultItemAnimator());

        videos = new ArrayList<>();
        videos.add(new Video());
        videos.add(new Video());
        videos.add(new Video());
        videoAdapter = new VideoAdapter(videos, this);

        videoTopList.setAdapter(videoAdapter);
    }

    @Override
    public void displayHome() {
        initVideoTopList();
    }

    @Override
    public void onLoadingFail(String errorMessage) {

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    public void onVideoClicked(Video video) {
        callback.onVideoClicked(video);
    }

    public interface Callback{
        void onVideoClicked(Video video);
    }

}
