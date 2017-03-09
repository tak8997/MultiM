package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final String email = PropertyManager.getInstance().getId();

                //로그인 되어있을 경우
                if(!email.equals("")) {
                    final String password = PropertyManager.getInstance().getPassword();

                    // TODO: db관련으로 바꿔야함.
                    PropertyManager.getInstance().getAuthWithIdPassword(email, password, new PropertyManager.OnResultListener<User>() {
                        @Override
                        public void onSuccess(User result) {
                            Toast.makeText(SplashActivity.this, email + ", " + password + "", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SplashActivity.this, MultiMemoActivity.class);
                            startActivity(intent);
                            finish();
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
            }
        }, SPLASH_TIME_OUT);
    }
}
