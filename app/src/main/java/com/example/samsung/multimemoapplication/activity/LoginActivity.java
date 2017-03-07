package com.example.samsung.multimemoapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.manager.PropertyManager;

import java.io.File;

/**
 * Created by Tak on 2017. 2. 21..
 */
public class LoginActivity extends AppCompatActivity {
    private EditText userID;
    private EditText userPassword;
    private Button signIn;
    private TextView signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userID = (EditText) findViewById(R.id.userID);
        userPassword = (EditText) findViewById(R.id.userPassword);
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (TextView) findViewById(R.id.signUp);

        signIn.setOnClickListener(listener);
        signUp.setOnClickListener(listener);
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
        String id = userID.getText().toString();
        String password = userPassword.getText().toString();

        if (id.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            userID.setError("enter a valid email address");
            valid = false;
        } else {
            userID.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            userPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            userPassword.setError(null);
        }

        if (id != PropertyManager.getInstance().getId() || password != PropertyManager.getInstance().getPassword()) {
            Toast.makeText(this, "Please check your Account", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    @Override
    public File[] getExternalCacheDirs() {
        return super.getExternalCacheDirs();
    }
}
