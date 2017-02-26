package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.manager.NetworkManager;
import com.example.samsung.multimemoapplication.manager.PropertyManager;
import com.example.samsung.multimemoapplication.model.User;

/**
 * Created by Tak on 2017. 2. 21..
 */

public class SplashActivity extends AppCompatActivity{
    private static String TAG = "SplashActivity";
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String id = PropertyManager.getInstance().getId();
        //로그인 되어있을 경우
        if(!id.equals("")) {
            String password = PropertyManager.getInstance().getPassword();
            PropertyManager.getInstance().getAuthWithIdPassword(id, password, new PropertyManager.OnResultListener<User>() {
                @Override
                public void onSuccess(User result) {
                    if(result != null) {
                        Intent intent = new Intent(SplashActivity.this, MultiMemoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFail(int code) {
                    Log.d(TAG, "Auth Failed");
                }
            });
        } //로그인 안되어 있을 경우
        else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MultiMemoActivity.class);
//                startActivity(intent);
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
    }
}
