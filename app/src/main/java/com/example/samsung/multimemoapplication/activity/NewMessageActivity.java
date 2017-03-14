package com.example.samsung.multimemoapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.manager.DBManagger;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.util.Date;

/**
 * Created by SAMSUNG on 2017-01-17.
 */
public class NewMessageActivity extends AppCompatActivity{
    private Button textBtn;
    private Button handBtn;
    private EditText text = null;

    private Button saveBtn;
    private Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        init();
    }

    private void init() {
        textBtn = (Button) findViewById(R.id.text_writing_btn);
        text = (EditText) findViewById(R.id.text);
        saveBtn = (Button) findViewById(R.id.save);
        closeBtn = (Button) findViewById(R.id.close);

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMessageActivity.this, "textBtn", Toast.LENGTH_SHORT).show();

                text.requestFocus();
                text.setFocusableInTouchMode(true);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                String memo = text.getText().toString();

                MemoList memoList = new MemoList(date.toString(), memo);

                DBManagger.getInstance().addMemo(memoList);

                Toast.makeText(NewMessageActivity.this, "Success?", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}
