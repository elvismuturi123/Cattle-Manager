package com.example.cattlemanager;  // Match this with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.Toolbox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser == null) {
                // No user is signed in, go to Welcome screen
                Toolbox.navigateTo(getApplicationContext(), Welcome.class);
                finish();
            }else{
                //there is a user signed in go to home screen
                Toolbox.navigateTo(getApplicationContext(), Login.class);
                finish();
            }
        },1000);
    }
    private void navigateToWelcome() {
        Intent intent = new Intent(Splash.this, Welcome.class);
        startActivity(intent);
        finish();
    }
}
