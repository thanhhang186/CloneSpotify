package com.khtn.clonespotify.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    private static String PREF_NAME = "prefs";
    public static final String USER_ID = "userId";
    public static final String DEVICE_CONTROL_ID = "deviceControlId";
    public static final String DEVICE_CURRENT_ID = "deviceCurrentId";
    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    public static void putUserID(Context context, String string){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(USER_ID, string);
        editor.commit();
    }

    public static String getUserId(Context context){
        return getPref(context).getString(USER_ID,"");
    }


    public static void putDeviceControlId(Context context, String id){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(DEVICE_CONTROL_ID, id);
        editor.commit();
    }

    public static String getDeviceControlId(Context context){
        return getPref(context).getString(DEVICE_CONTROL_ID, "");
    }

    public static void putDeviceCurrentId(Context context, String id){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(DEVICE_CURRENT_ID, id);
        editor.commit();
    }

    public static String getDeviceCurrentlId(Context context){
        return getPref(context).getString(DEVICE_CURRENT_ID, "");
    }

    public static void removeDeviceControlId(Context context){
        SharedPreferences settings = context.getSharedPreferences(DEVICE_CONTROL_ID, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }


}
