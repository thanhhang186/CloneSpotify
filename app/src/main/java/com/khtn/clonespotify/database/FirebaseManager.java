package com.khtn.clonespotify.database;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.khtn.clonespotify.detail.abstracts.VideoAbstract;
import com.khtn.clonespotify.detail.adapter.DeviceAbstract;
import com.khtn.clonespotify.model.Device;
import com.khtn.clonespotify.model.User;
import com.khtn.clonespotify.model.Video;
import com.khtn.clonespotify.sign.view.SignInView;
import com.khtn.clonespotify.utils.Constants;
import com.khtn.clonespotify.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {
    private static final String TAG = "firebase";
    private static FirebaseManager instance;

    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    public List<Device> devices = new ArrayList<>();
    public List<Video> videos = new ArrayList<>();
    public Video video;

    public void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
    }

    private FirebaseManager() {
        initFirebase();
    }

    public synchronized static FirebaseManager getInstance() {
        if (instance == null) {
            synchronized (FirebaseManager.class) {
                if (instance == null) {
                    instance = new FirebaseManager();
                }
            }
        }
        return instance;
    }

    public synchronized void signUpUser(Activity activity, String id, String email, String password) throws Exception {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        try {
                            saveUser(id, new User(id, user.getUid(), email));
                            Toast.makeText(activity, "Registration successful" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public synchronized void saveUser(String id, User user) throws Exception {
        // add to database
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_USERS);
        mFirebaseDatabase.child(id).setValue(user);
    }

    public void getUserByID(Activity activity, String userID) {
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_USERS);
        Query myQuery = mFirebaseDatabase.orderByChild("uid").equalTo(userID);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    try {
                        User user = postSnapshot.getValue(User.class);
                        Log.i(TAG, "id: " + user.getId());
                        PrefUtils.putUserID(activity, user.getId());
                        if(user.getDeviceControlID() != null){
                            PrefUtils.putDeviceControlId(activity, user.getDeviceControlID());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("onCancelled");

            }
        });
    }
    public void addDevice(String userID, Device device){
        DatabaseReference databaseReference = mFirebaseInstance.getReference(Constants.DATABASE_DEVICES);
        //databaseReference.child(userID).setValue(device.getDeviceID());
        databaseReference.child(userID).child(device.getDeviceID()).setValue(device.getDeviceName());
    }

    public synchronized void signInUser(Activity activity, SignInView signInView,  String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        getUserByID(activity, user.getUid());
                        signInView.loginSuccess();
                        signInView.loadHomeAct();
                    } else {
                        signInView.loginError();
                        Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public List<Video> getVideoTopList(){
        List<Video> videos = new ArrayList<>();
        return videos;
    }

    public void setVideoCurrent(String userID, Video video){
            DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_VIDEO);
            mFirebaseDatabase.child(userID).setValue(video);
    }

    public void getVideoCurrent(Context context, String userID, ValueEventListener valueEventListener){
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_VIDEO);
        Query myQuery = mFirebaseDatabase.child(userID);
        myQuery.addValueEventListener(valueEventListener);
    }

    public void getDeviceListByID(String userID, DeviceAbstract deviceAdapter){
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_DEVICES);
        Query myQuery = mFirebaseDatabase.child(userID);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String value = dataSnapshot1.getValue(String.class);
                    Log.i(TAG, "device_name: " + value);
                    Device device = new Device();
                    device.setDeviceName(value);
                    String deviceID = dataSnapshot1.getKey();
                    device.setDeviceID(deviceID);
                    Log.i(TAG, "device_id " + deviceID);
                    deviceAdapter.getDevices().add(device);
                    deviceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setDeviceControlID(String userID, String deviceID){
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_USERS);
        mFirebaseDatabase.child(userID).child("deviceControlID").setValue(deviceID);
    }

    public void removeDeviceControlID(String userID, String deviceID){
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_USERS);
        mFirebaseDatabase.child(userID).child("deviceControlID").removeValue();
    }
    public void removeVideoCurrent(String userID){
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE_VIDEO);
        mFirebaseDatabase.child(userID).removeValue();
    }


}
