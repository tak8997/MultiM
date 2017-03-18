package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.manager.DBManagger;
import com.example.samsung.multimemoapplication.manager.PropertyManager;
import com.example.samsung.multimemoapplication.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tak on 2017. 2. 21..
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static DBManagger dbManagger;
    private CallbackManager callbackManager;

    @BindView(R.id.userEmail) EditText userEmail;
    @BindView(R.id.userPassword) EditText userPassword;
    @BindView(R.id.login_button) LoginButton loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        ButterKnife.bind(this);

        dbManagger = DBManagger.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }

    @OnClick({R.id.signIn, R.id.signUp, R.id.login_button})
    public void onSignClicked(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                signInWithEmailPassword();
                break;
            case R.id.signUp:
                signUpWithEmailPassword();
                break;
            case R.id.login_button:
                signInWithFacebook();
                break;
        }
    }

    // 페이스북을 통한 로그인
    private void signInWithFacebook() {
        // Callback registration
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userFacebookToken = loginResult.getAccessToken().toString();

                PropertyManager.getInstance().setUserFacebookToken(userFacebookToken);

                Intent intent = new Intent(LoginActivity.this, MultiMemoActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }

    // 이메일, 패스워드 로그인
    private void signInWithEmailPassword() {
        if (!validateUserAccount()) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            return;
        }
        PropertyManager.getInstance().setEmail(userEmail.getText().toString());
        PropertyManager.getInstance().setPassword(userPassword.getText().toString());

        Intent intent = new Intent(LoginActivity.this, MultiMemoActivity.class);
        startActivity(intent);
        finish();
    }

    // 회원가입
    private void signUpWithEmailPassword() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUserAccount() {
        Boolean valid = true;
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("enter a valid email address");
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            userPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        if(!checkUserWithDB(email)) {
            Toast.makeText(this, "User not exist", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    private boolean checkUserWithDB(String email) {
        User user = dbManagger.getUser(email);

        Log.d("checkUserWithDB", user.getEmail());
        if(user != null) {
            if(user.getPassword().equals(userPassword.getText().toString().trim()))
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public File[] getExternalCacheDirs() {
        return super.getExternalCacheDirs();
    }
}
