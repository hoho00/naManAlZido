package com.hackerton.googlemap.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hackerton.googlemap.fragment.CommunityMapfragment;
import com.hackerton.googlemap.fragment.ReviewMapfragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int PageCounter;

    public PageAdapter(@NonNull FragmentManager fm, int pageCounter) {
        super(fm);
        this.PageCounter = pageCounter;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ReviewMapfragment tabFragment1 = new ReviewMapfragment();
                return tabFragment1;
            case 1:
                CommunityMapfragment tabFragment2 = new CommunityMapfragment();
                return tabFragment2;
            case 2:
                ReviewMapfragment tabFragment3 = new ReviewMapfragment();
                return tabFragment3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return PageCounter ;
    }
}
