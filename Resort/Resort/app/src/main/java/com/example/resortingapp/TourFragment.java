package com.example.resortingapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import resortingapp.R;
import com.google.android.material.tabs.TabLayout;


public class TourFragment extends Fragment {
    private Typeface mTypeface;
    ViewPager mViewPager;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_tour, container, false);

        TabLayout tabLayout = mView.findViewById(R.id.tablayout);
        mViewPager = mView.findViewById(R.id.tour_view_pager);
        ToursViewPagerAdapter adapter = new ToursViewPagerAdapter(mView.getContext(), getFragmentManager());
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        int position = 0;
        if (getArguments() != null)
            position = getArguments().getInt("TARGET_FRAGMENT_ID");
        mViewPager.setCurrentItem(position);
//        ViewGroup tabView = (ViewGroup) tabLayout.getChildAt(position);
//        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/cairo.ttf");
//        int tabChildCount = tabView.getChildCount();
//        for (int i = 0; i < tabChildCount; i++) {
//            View tabViewChild = tabView.getChildAt(i);
//            if (tabViewChild instanceof TextView) {
//                ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
//            }
//        }
        return mView;
    }


}
