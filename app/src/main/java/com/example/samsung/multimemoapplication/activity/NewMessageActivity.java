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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SAMSUNG on 2017-01-17.
 */
public class NewMessageActivity extends AppCompatActivity{
    private static final String TAG = "NewMessageActivity";

    private static DBManagger dbManagger;

    @BindView(R.id.text) EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        init();
    }

    private void init() {
        ButterKnife.bind(this);
        dbManagger = DBManagger.getInstance();
    }

    @OnClick({R.id.save, R.id.close, R.id.text})
    public void messageClicked(View v) {
        switch (v.getId()) {
            case R.id.save:
                saveMemo();
                break;
            case R.id.close:
                closeMemo();
                break;
            case R.id.text:
                focusMemo();
                break;
        }
    }

    private void focusMemo() {
        text.requestFocus();
        text.setFocusableInTouchMode(true);
    }

    private void closeMemo() {
        finish();
    }

    private void saveMemo() {
        Date date = new Date();
        String memo = text.getText().toString();

        if(!memo.equals("")) {
            MemoList memoList = new MemoList(date.toString(), memo);

            dbManagger.addMemo(memoList);

            finish();
        } else {
            focusMemo();
            Toast.makeText(this, "Text is Empty!!", Toast.LENGTH_SHORT).show();
        }
    }

}
