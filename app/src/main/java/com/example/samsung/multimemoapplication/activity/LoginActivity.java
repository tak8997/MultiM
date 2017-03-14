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

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tak on 2017. 2. 21..
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static DBManagger dbManagger;

    @BindView(R.id.userEmail) EditText userEmail;
    @BindView(R.id.userPassword) EditText userPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        ButterKnife.bind(this);
        dbManagger = DBManagger.getInstance();
    }

    @OnClick({R.id.signIn, R.id.signUp})
    public void onSignClicked(View v) {
        switch (v.getId()) {
            case R.id.signIn:
                signInWithEmailPassword();
                break;
            case R.id.signUp:
                signUpWithEmailPassword();
                break;
        }
    }

    // 로그인
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
    public File[] getExternalCacheDirs() {
        return super.getExternalCacheDirs();
    }
}
