package com.hackerton.googlemap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hackerton.googlemap.R;
import com.hackerton.googlemap.model.ReviewItem;

import java.util.ArrayList;

public class MyReviewAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ReviewItem> review;

    public MyReviewAdapter(Context context, ArrayList<ReviewItem> data){
        mContext = context;
        review = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return review.size();
    }

    @Override
    public Object getItem(int position) {
        return review.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview, null);

        TextView my_title = (TextView)view.findViewById(R.id.my_title);
        TextView my_content = (TextView)view.findViewById(R.id.my_content);

        my_title.setText("리뷰 " + position);
        my_content.setText(review.get(position).getReview());

        return view;
    }
}
