package com.example.samsung.multimemoapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.database.MultiMemoDBHelper;
import com.example.samsung.multimemoapplication.manager.PropertyManager;
import com.example.samsung.multimemoapplication.model.User;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tak on 2017. 2. 21..
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private static MultiMemoDBHelper multiMemoDBHelper;

    @BindView(R.id.userEmail) EditText userEmail;
    @BindView(R.id.userPassword) EditText userPassword;
    @BindView(R.id.signIn) Button signInBtn;
    @BindView(R.id.signUp) Button signUpBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        ButterKnife.bind(this);

        signInBtn.setOnClickListener(listener);
        signUpBtn.setOnClickListener(listener);
    }

    @Override
    public void onStart() {
        super.onStart();

        openDatabases();
    }

    private void openDatabases() {
        multiMemoDBHelper = MultiMemoDBHelper.getInstance();
        if(multiMemoDBHelper != null)
            Log.d(TAG, "Memo database is open.");
        else
            Log.d(TAG, "Memo database is not open.");
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signIn:
                    signInWithEmailPassword();
                    break;
                case R.id.signUp:
                    signUpWithEmailPassword();
                    break;
            }
        }
    };

    private void signInWithEmailPassword() {
        if (!validateUserAccount()) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(LoginActivity.this, MultiMemoActivity.class);
        startActivity(intent);
        finish();
    }

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
            valid = false;
        }

        return valid;
    }

    private boolean checkUserWithDB(String email) {
        User user = multiMemoDBHelper.getUser(email);

        if(user != null)
            return true;

        return false;
    }

    @Override
    public File[] getExternalCacheDirs() {
        return super.getExternalCacheDirs();
    }
}
