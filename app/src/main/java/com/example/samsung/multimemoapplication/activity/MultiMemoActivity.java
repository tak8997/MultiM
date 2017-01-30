package com.example.samsung.multimemoapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.adapter.MyAdapter;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SAMSUNG on 2017-01-15.
 */

public class MultiMemoActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_memo);

        // recycler view 세팅
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 메모 삽입
        ArrayList<MemoList> items = new ArrayList<>();
        items.add(new MemoList(R.drawable.mini, new Date(), "first recycler view"));

        mAdapter = new MyAdapter(items);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);

        // 새 매모 버튼
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MultiMemoActivity.this, NewMessageActivity.class);
                startActivity(intent);
            }
        });
    }
}
