package com.hackerton.googlemap.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hackerton.googlemap.R;
import com.hackerton.googlemap.fragment.ReviewFragment;
import com.hackerton.googlemap.model.ReviewItem;

import java.util.ArrayList;

public class MyReviewAdapter extends FragmentPagerAdapter {

    private ArrayList<ReviewFragment> hope;
    private FragmentManager FM;

    public MyReviewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        hope = new ArrayList<ReviewFragment>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return hope.get(position);
    }

    @Override
    public int getCount() {
        return hope.size();
    }

    @Override
    public void notifyDataSetChanged() {

    }

    public void addItem(ReviewFragment reviewFragment){
        hope.add(reviewFragment);
    }
}
