package com.example.cattlemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.cattlemanager.Classses.Toolbox;

public class HomeTocRecs extends AppCompatActivity {
    Button navigateToDispMilk;

    Button exploreDC;
// Explore to display cattle details
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_toc_recs);


        exploreDC = findViewById(R.id.navHomeTocRec_button);
        navigateToDispMilk = findViewById(R.id.navHomeToDispMilk_button);

        exploreDC.setOnClickListener(v -> Toolbox.navigateTo(HomeTocRecs.this, Display_cattle.class));
        navigateToDispMilk.setOnClickListener(v -> Toolbox.navigateTo(HomeTocRecs.this, Display_milk.class));




    }
}