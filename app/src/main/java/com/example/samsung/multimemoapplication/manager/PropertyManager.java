package com.example.samsung.multimemoapplication.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.samsung.multimemoapplication.MyApplication;
import com.example.samsung.multimemoapplication.model.User;

/**
 * Created by Tak on 2017. 2. 21..
 */

public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null)
            instance = new PropertyManager();

        return instance;
    }

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(int code);
    }

    private OnResultListener listener;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        mEditor = mPrefs.edit();
    }

    public void setOnResultListener(OnResultListener listener) {
        this.listener = listener;
    }

    public static final String USER_ID = "id";
    public static final String USER_PASSWORD = "password";

    public void setId(String id) {
        mEditor.putString(USER_ID, id);
        mEditor.commit();
    }

    public String getId() {
        return mPrefs.getString(USER_ID, "");
    }

    public void setPassword(String password) {
        mEditor.putString(USER_PASSWORD, password);
        mEditor.commit();
    }

    public String getPassword() {
        return mPrefs.getString(USER_PASSWORD, "");
    }

    public void getAuthWithIdPassword(String id, String password, OnResultListener<User> listener) {
        setOnResultListener(listener);
        getIdPassword(id, password);
    }

    private void getIdPassword(String id, String password) {
        if(listener != null && id == getId() && password == getPassword()) {
            User user = new User(id, password);

            listener.onSuccess(user);
        }
    }


    public boolean isBackupSync() {
        return mPrefs.getBoolean("perf_sync", false);
    }

}
