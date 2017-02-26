package com.example.samsung.multimemoapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tak on 2017. 2. 21..
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
