package com.example.cattlemanager;  // Match this with your actual package name

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToWelcome();
            }
        }, 4000);
    }

    private void navigateToWelcome() {
        Intent intent = new Intent(Splash.this, Welcome.class);
        startActivity(intent);
        finish();
    }
}
