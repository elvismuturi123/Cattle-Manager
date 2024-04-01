package com.example.cattlemanager;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cattlemanager.Classses.Toolbox;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SalesPageActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;

    Button btnViewOrders, btnViewReports;

    DatabaseReference salesRef = FirebaseDatabase.getInstance().getReference("Sales");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_page);

//
//        // Get class data
//         MilkProduct milkProduct = new MilkProduct(productID, cBreed, cDate, cTotal, cPrice, cNotes);
//        salesRef.child(breed).push().setValue(milkProduct);


        tabLayout = findViewById(R.id.salesMilkTabLayout);
        viewPager2 = findViewById(R.id.viewPagerSales);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        btnViewOrders = findViewById(R.id.btnViewOrders);
        btnViewReports =findViewById(R.id.btnViewSalesReport);


        btnViewOrders.setOnClickListener(v -> {
            Toolbox.navigateTo(getApplicationContext(), Vieworders.class);
        });

        btnViewReports.setOnClickListener(v -> {
            Toolbox.navigateTo(getApplicationContext(), Reports.class);
        });


       tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager2.setCurrentItem(tab.getPosition());
           }
           @Override
           public void onTabUnselected(TabLayout.Tab tab) {
           }
           @Override
           public void onTabReselected(TabLayout.Tab tab) {
           }
       });

       // Handle underline function upon swipe
       viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageSelected(int position) {
               super.onPageSelected(position);
               tabLayout.getTabAt(position).select();
           }
       });
    }
}