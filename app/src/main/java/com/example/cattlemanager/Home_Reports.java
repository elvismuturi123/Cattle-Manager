package com.example.cattlemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cattlemanager.ProductionReport;
import com.example.cattlemanager.R;

public class Home_Reports extends AppCompatActivity {
    CardView milkCard,cattleCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_reports);

        milkCard=findViewById(R.id.milk_card);

        cattleCard=findViewById(R.id.cattle_card);
        milkCard.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ProductionReport.class)));

        cattleCard.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CattleReport.class)));

    }
}