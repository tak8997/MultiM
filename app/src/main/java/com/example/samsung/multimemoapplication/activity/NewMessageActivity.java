package com.example.samsung.multimemoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.database.MultiMemoDB;

/**
 * Created by SAMSUNG on 2017-01-17.
 */
public class NewMessageActivity extends AppCompatActivity{
    private MultiMemoDB database;

    private Button textBtn;
    private Button handBtn;
    private EditText text;

    private Button saveBtn;
    private Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        textBtn = (Button) findViewById(R.id.text_writing_btn);
        handBtn = (Button) findViewById(R.id.hand_writing_btn);
        text = (EditText) findViewById(R.id.text);

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMessageActivity.this, "textBtn", Toast.LENGTH_SHORT).show();

                text.requestFocus();
                text.setFocusableInTouchMode(true);
            }
        });

        handBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMessageActivity.this, "handBtn", Toast.LENGTH_SHORT).show();

                text.setFocusable(false);
            }
        });
    }




}
