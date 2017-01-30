package com.example.samsung.multimemoapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samsung.multimemoapplication.R;
import com.example.samsung.multimemoapplication.model.MemoList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SAMSUNG on 2017-01-15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MemoList> mItems;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MemoList> items) {
        mItems = items;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public TextView date;
        public TextView memo;


        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            date = (TextView) v.findViewById(R.id.date);
            memo = (TextView) v.findViewById(R.id.memo);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        Date curDate = mItems.get(position).getCurDate();
        String curretnTimeString = new SimpleDateFormat("HH:mm:ss").format(curDate);

        holder.image.setImageResource(mItems.get(position).getImage());
        holder.date.setText(curretnTimeString);
        holder.memo.setText(mItems.get(position).getMemo());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
