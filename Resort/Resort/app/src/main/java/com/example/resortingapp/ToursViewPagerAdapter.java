package com.example.resortingapp;

import android.content.Context;
import android.graphics.Typeface;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import resortingapp.R;

public class ToursViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ToursViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TopRatedTourFragment();
            case 1:
                return new RecommendedTourFragment();
            case 2:
                return new GuiderProfileFragment();
            default:
                return new TopRatedTourFragment();
        }
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.top_rated);
            case 1:
                return context.getString(R.string.recommended);
            case 2:
                return context.getString(R.string.tourguider);
            default:
                return context.getString(R.string.top_rated);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
