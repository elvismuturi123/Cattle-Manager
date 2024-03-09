package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cattlemanager.Fragments.AyrshireFragment;
import com.example.cattlemanager.Fragments.FriesianFragment;
import com.example.cattlemanager.Fragments.Guernsey;
import com.example.cattlemanager.Fragments.HolsteinFragment;
import com.example.cattlemanager.Fragments.JerseyFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new JerseyFragment();
            case 1:
                return new Guernsey();
            case 2:
                return new AyrshireFragment();
            case 3:
                return new HolsteinFragment();
            case 4:
                return new FriesianFragment();
            default:
                return new JerseyFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
