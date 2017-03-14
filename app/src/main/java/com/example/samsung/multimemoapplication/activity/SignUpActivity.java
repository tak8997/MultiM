package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.database.MultiMemoDBHelper;
import com.example.samsung.multimemoapplication.manager.PropertyManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tak on 2017. 2. 22..
 */
public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private static MultiMemoDBHelper multiMemoDBHelper;

    @BindView(R.id.userEmail) EditText userEmail;
    @BindView(R.id.userName) EditText userName;
    @BindView(R.id.userPassword) EditText userPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        
        init();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        operDatabases();
    }

    private void operDatabases() {
        multiMemoDBHelper = MultiMemoDBHelper.getInstance();
        if(multiMemoDBHelper != null)
            Log.d(TAG, "Memo database is open.");
        else
            Log.d(TAG, "Memo database is not open.");
    }

    @OnClick(R.id.signUp)
    public void onSignUpClicked() {
        signUpWithEmailPassword();
    }

    private void signUpWithEmailPassword() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String name = userName.getText().toString();

        if(!validateUserAccount(name, email, password)) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: use callback
        multiMemoDBHelper.insertUser(email, password);

        PropertyManager.getInstance().setEmail(email);
        PropertyManager.getInstance().setPassword(password);

        Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUserAccount(String name, String email, String password) {
        Boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            userName.setError("at least 3 characters");
            valid = false;
        } else {
            userName.setError(null);
        }

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

        return valid;
    }
}
