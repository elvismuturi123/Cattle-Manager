package com.example.cattlemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class ProductionReport extends AppCompatActivity {
    MaterialToolbar materialToolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_report);

        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.simpleTabLayout);
        materialToolbar=findViewById(R.id.toolBar);

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //add the various tabs

        tabLayout.addTab(tabLayout.newTab().setText("Reports"));
        tabLayout.addTab(tabLayout.newTab().setText("Milk Report"));
         //tabLayout.addTab(tabLayout.newTab().setText(""));


        TabLayoutAdapter adapter= new TabLayoutAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}