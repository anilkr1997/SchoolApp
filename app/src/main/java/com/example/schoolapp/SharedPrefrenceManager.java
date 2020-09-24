package com.example.schoolapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefrenceManager {
    //the constants
    private static final String SHARED_PREF_NAME = "MyPref";
    private static final String KEY_USERNAME = "ashish";
    private static final String KEY_EMAIL = "ashishsikarwar";
    private static final String Password = "password";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";

    private static SharedPrefrenceManager mInstance;
    private static Context mCtx;

    private SharedPrefrenceManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefrenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefrenceManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(String user,String name,String password) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, user);
        editor.putString(KEY_USERNAME, name);
        editor.putString(Password, password);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public String getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


               String s= sharedPreferences.getString(KEY_EMAIL, null);
    return s;
    }
    public String getPass() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


               String s= sharedPreferences.getString(Password, null);
    return s;
    }
    public String getName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
               String s= sharedPreferences.getString(KEY_USERNAME, null);
    return s;
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
