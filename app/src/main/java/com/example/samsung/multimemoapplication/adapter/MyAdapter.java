package com.example.samsung.multimemoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.activity.MultiMemoActivity;
import com.example.samsung.multimemoapplication.activity.ShowMessageActivity;
import com.example.samsung.multimemoapplication.common.BasicInfo;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SAMSUNG on 2017-01-15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static List<MemoList> mItems;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<MemoList> items) {
        mItems = items;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private Context context;
        public TextView date;
        public TextView memo;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            context = v.getContext();
            date = (TextView) v.findViewById(R.id.date);
            memo = (TextView) v.findViewById(R.id.memo);
        }

        @Override
        public void onClick(View v) {
            showMessage(getLayoutPosition());
        }

        private void showMessage(int layoutPosition) {
            Toast.makeText(context, layoutPosition + "", Toast.LENGTH_SHORT).show();
            MemoList memoList = mItems.get(layoutPosition);

            Intent intent = new Intent(context, ShowMessageActivity.class);
            intent.putExtra("memo", memoList);
            context.startActivity(intent);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Date date = new Date();
        String insertDate = BasicInfo.dateDayFormat.format(date);
        
        holder.date.setText(insertDate);
        holder.memo.setText(mItems.get(position).getMemo());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
