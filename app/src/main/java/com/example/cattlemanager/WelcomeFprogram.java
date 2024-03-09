package com.example.cattlemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class WelcomeFprogram extends AppCompatActivity {


    Button navToFprogram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_fprogram);

        navToFprogram = findViewById(R.id.welFeed_button);

        navToFprogram.setOnClickListener(v -> Toolbox.navigateTo(WelcomeFprogram.this, View_Feed_Program.class));




    }
}