package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.adapter.MyAdapter;
import com.example.samsung.multimemoapplication.manager.DBManagger;
import com.example.samsung.multimemoapplication.manager.PropertyManager;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SAMSUNG on 2017-01-15.
 */

public class MultiMemoActivity extends AppCompatActivity {
    public static final String TAG = "MultiMemoActivity";

    public static DBManagger dbManagger;

    @BindView(R.id.my_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_memo);
        
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        dbManagger = DBManagger.getInstance();

        setSupportActionBar(toolbar);

        // recycler view 세팅, use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    // 새 매모 버튼
    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(MultiMemoActivity.this, NewMessageActivity.class);
        startActivity(intent);
    }

    public void onStart() {
        super.onStart();
        Log.d("onStart", "Memo database is loaded");

        loadMemoList();
    }

    public void onResume() {
        super.onResume();
        Log.d("onResume", "Memo database is reloaded");

        loadMemoList();
    }

    private void loadMemoList() {
        dbManagger = DBManagger.getInstance();
        List<MemoList> memoLists = dbManagger.getAllMemoLists();

        mAdapter = new MyAdapter(memoLists);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void logOut() {
        PropertyManager.getInstance().userClear();

        Intent intent = new Intent(MultiMemoActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
