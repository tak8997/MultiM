package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.manager.DBManagger;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tak on 2017. 3. 6..
 */
public class ShowMessageActivity extends AppCompatActivity {
    private static final String TAG = "ShowMessageActivity";

    @BindView(R.id.inputDate) TextView curDate;
    @BindView(R.id.content) EditText content;
    @BindView(R.id.save) TextView save;
    @BindView(R.id.delete) TextView delete;
    @BindView(R.id.close) TextView close;

    private MemoList memoList;
    private DBManagger dbManagger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);

        init();
    }

    private void init() {
        ButterKnife.bind(this);
        dbManagger = DBManagger.getInstance();

        Intent intent = getIntent();
        memoList = (MemoList) intent.getSerializableExtra("memo");

        curDate.setText(memoList.getCurDate().toString());
        content.setText(memoList.getMemo().toString());

        save.setOnClickListener(listener);
        close.setOnClickListener(listener);
        delete.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save:
                    saveMemo();
                    break;
                case R.id.close:
                    closeMemo();
                    break;
                case R.id.delete:
                    deleteMemo();
                    break;
            }
        }
    };

    private void closeMemo() {
        finish();
    }

    private void deleteMemo() {
        dbManagger.deleteMemo(memoList);
        Toast.makeText(this, "deleteMemo", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void saveMemo() {
        Date curDate = new Date();
        String memoContent = content.getText().toString();

        memoList.setCurDate(curDate.toString());
        memoList.setMemo(memoContent);

        dbManagger.updateMemo(memoList);

        Toast.makeText(this, "saveMemo", Toast.LENGTH_SHORT).show();

        finish();

    }
}
