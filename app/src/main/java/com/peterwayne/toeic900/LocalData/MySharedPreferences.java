package com.peterwayne.toeic900.LocalData;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    public static String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    public static String TEST_PREF = "TEST_PREF";
    private Context mContext;

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }
    public void putBooleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public boolean getBooleanValue(String key)
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES,MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TEST_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TEST_PREF, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    public void clearTestAnswers()
    {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(TEST_PREF, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }
}
