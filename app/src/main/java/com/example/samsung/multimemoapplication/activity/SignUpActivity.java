package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.manager.PropertyManager;

/**
 * Created by Tak on 2017. 2. 22..
 */
public class SignUpActivity extends AppCompatActivity {
    private EditText userID;
    private EditText userPassword;
    private EditText userName;
    private Button signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        userID = (EditText) findViewById(R.id.userID);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userName = (EditText) findViewById(R.id.userName);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signUpWithEmailPassword();
        }
    };

    private void signUpWithEmailPassword() {
        String id = userID.getText().toString();
        String password = userPassword.getText().toString();
        String name = userName.getText().toString();

        if(!validateUserAccount(name, id, password)) {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            return;
        }

        PropertyManager.getInstance().setEmail(id);
        PropertyManager.getInstance().setPassword(password);

        Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUserAccount(String name, String id, String password) {
        Boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            userName.setError("at least 3 characters");
            valid = false;
        } else {
            userName.setError(null);
        }

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

        return valid;
    }
}
