package com.example.cattlemanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    Context mContext;
    int num_Tabs;

    public TabLayoutAdapter(@NonNull FragmentManager fm, Context mContext, int num_Tabs) {
        super(fm);
        this.mContext = mContext;
        this.num_Tabs = num_Tabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CattleProductionReport();
            case 1:
                return new MilkingTimeReport();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num_Tabs;
    }
}
