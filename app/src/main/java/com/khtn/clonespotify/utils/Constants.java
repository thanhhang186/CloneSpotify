package com.khtn.clonespotify.utils;

import android.os.Build;
import android.text.TextUtils;

public class Constants {
    public static final String YOUTUBE_API_KEY = "AIzaSyBBcpxJYdnjLCoq95r6o8oZXrUfvcn73I0";
    public static final  String DATABASE_USERS = "users";
    public static final String DATABASE_DEVICES = "devices";
    public static final  String DATABASE_VIDEO = "videos";
    public static final String REGION_CODE= "VN";
    public static final int LIMIT_RECOMMEND = 10;
    public static final int MAX_VIDEO_TOP_LIST = 10;
    public static String linkPlaylist = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=%s&playlistId=%s&fields=items(snippet(title,thumbnails(high(url),default),resourceId(videoId))),nextPageToken,pageInfo&key=" + YOUTUBE_API_KEY;



}
