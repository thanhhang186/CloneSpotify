package com.khtn.clonespotify.api;
import android.util.Log;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.*;

import com.google.api.services.youtube.YouTube;
import com.khtn.clonespotify.database.FirebaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class YoutubeApi {
    private static final String TAG = YoutubeApi.class.getSimpleName();
    /** Application name. */
    private static final String APPLICATION_NAME = "API Sample";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
            ".credentials/youtube-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials at
     * ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Create an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = YoutubeApi.class.getResourceAsStream("/com/khtn/clonespotify/api/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized API client service, such as a YouTube Data API
     * client service.
     *
     * @return an authorized API client service
     * @throws IOException
     */
    public static YouTube getYouTubeService() throws IOException {
        Credential credential = authorize();
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void listMostPopularVideos() throws IOException {
        YouTube youtube = getYouTubeService();
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails,statistics");
            parameters.put("chart", "mostPopular");
            parameters.put("regionCode", "US"); //chang country using.
            parameters.put("videoCategoryId", "");

            YouTube.Videos.List videosListMostPopularRequest = youtube.videos().list(parameters.get("part").toString());
            if (parameters.containsKey("chart") && parameters.get("chart") != "") {
                videosListMostPopularRequest.setChart(parameters.get("chart").toString());
            }

            if (parameters.containsKey("regionCode") && parameters.get("regionCode") != "") {
                videosListMostPopularRequest.setRegionCode(parameters.get("regionCode").toString());
            }

            if (parameters.containsKey("videoCategoryId") && parameters.get("videoCategoryId") != "") {
                videosListMostPopularRequest.setVideoCategoryId(parameters.get("videoCategoryId").toString());
            }

            VideoListResponse response = videosListMostPopularRequest.execute(); //response format Json
            System.out.println(response);
            Log.d(TAG, response.toString());
        }catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println(
                    "There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void listActivitiesMyChanel() throws IOException {
        YouTube youtube = getYouTubeService();

        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails");
            parameters.put("maxResults", "25");
            parameters.put("mine", "true");

            YouTube.Activities.List activitiesListMineRequest = youtube.activities()
                    .list(parameters.get("part").toString());
            if (parameters.containsKey("maxResults")) {
                activitiesListMineRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
            }

            if (parameters.containsKey("mine") && parameters.get("mine") != "") {
                boolean mine = (parameters.get("mine") == "true") ? true : false;
                activitiesListMineRequest.setMine(mine);
            }

            ActivityListResponse response = activitiesListMineRequest.execute();
            System.out.println(response);

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println(
                    "There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // Sample java code for channelSections.list
    public void channelSections(String channelId) throws IOException {
        // teams = new HashMap<>();
        YouTube youtube = getYouTubeService();

        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "contentDetails");
            parameters.put("channelId", channelId); // example: UC_x5XG1OV2P6uZZ5FSM9Ttw

            YouTube.ChannelSections.List channelSectionsListByIdRequest = youtube.channelSections()
                    .list(parameters.get("part").toString());
            if (parameters.containsKey("channelId") && parameters.get("channelId") != "") {
                channelSectionsListByIdRequest.setChannelId(parameters.get("channelId").toString());
            }

            ChannelSectionListResponse response = channelSectionsListByIdRequest.execute();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    String playlistId = jsonArray.getJSONObject(i).getJSONObject("contentDetails")
                            .getJSONArray("playlists").get(0).toString();
                    System.out.println(playlistId);
                    playlistItems(playlistId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println(
                    "There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    // Sample java code for playlistItems.list
    public void playlistItems(String playlistId) throws IOException {

        YouTube youtube = getYouTubeService();

        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet");
            parameters.put("maxResults", "50");
            parameters.put("playlistId", playlistId); // example PLIivdWyY5sqL-4yjTf-kPxPuJbceWKfwJ

            YouTube.PlaylistItems.List playlistItemsListByPlaylistIdRequest = youtube.playlistItems()
                    .list(parameters.get("part").toString());
            if (parameters.containsKey("maxResults")) {
                playlistItemsListByPlaylistIdRequest
                        .setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
            }

            if (parameters.containsKey("playlistId") && parameters.get("playlistId") != "") {
                playlistItemsListByPlaylistIdRequest.setPlaylistId(parameters.get("playlistId").toString());
            }

            PlaylistItemListResponse response = playlistItemsListByPlaylistIdRequest.execute();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    String title = jsonArray.getJSONObject(i).getJSONObject("snippet").getString("title");
                    String linkVideo = jsonArray.getJSONObject(i).getJSONObject("snippet").getJSONObject("resourceId")
                            .getString("videoId");
                    String description = jsonArray.getJSONObject(i).getJSONObject("snippet").getString("description");
                    System.out.println(title + " - " + linkVideo);
                    getFullInfoVideo(title, linkVideo, description);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println(
                    "There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void getFullInfoVideo(String title, String videoId, String description) throws IOException {
        YouTube youtube = getYouTubeService();
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "contentDetails,statistics");
            parameters.put("id", videoId);

            YouTube.Videos.List videosListByIdRequest = youtube.videos().list(parameters.get("part").toString());
            if (parameters.containsKey("id") && parameters.get("id") != "") {
                videosListByIdRequest.setId(parameters.get("id").toString());
            }

            VideoListResponse response = videosListByIdRequest.execute();

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    String duration = jsonArray.getJSONObject(i).getJSONObject("contentDetails").getString("duration");

                    /*PeriodFormatter formatter = ISOPeriodFormat.standard();
                    Period period = formatter.parsePeriod(duration);
                    Seconds seconds = period.toStandardSeconds();
                    int minutes = seconds.getSeconds() / 60;
                    int sec = seconds.getSeconds() % 60;
                    String durationFormat = String.format("%d:%02d", minutes, sec);*/

                    long viewCount = jsonArray.getJSONObject(i).getJSONObject("statistics").getLong("viewCount");
                    long likeCount = jsonArray.getJSONObject(i).getJSONObject("statistics").getLong("likeCount");

                    System.out.println("" + " : " + viewCount + " : " + likeCount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println(
                    "There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}