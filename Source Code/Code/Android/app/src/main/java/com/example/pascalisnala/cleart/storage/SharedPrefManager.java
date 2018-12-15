package com.example.pascalisnala.cleart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pascalisnala.cleart.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static SharedPrefManager mInstance;

    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;

    }


    public void saveUser(User user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("userid", user.getUserid());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("phonenum", user.getPhonenum());
        editor.putString("username", user.getUsername());

        editor.apply();

    }

    public boolean isLoggedin() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("userid", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User user = new User(
                sharedPreferences.getInt("userid",-1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phonenum", null),
                sharedPreferences.getString("username", null)
        );
        return user;
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
