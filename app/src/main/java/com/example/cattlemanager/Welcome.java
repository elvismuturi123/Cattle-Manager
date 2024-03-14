package com.example.cattlemanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button login = findViewById(R.id.btn_goToLogin);
        Button register = findViewById(R.id.btn_goToRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.navigateTo(Welcome.this, SalesPageActivity.class);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.navigateTo(Welcome.this, Home.class);
            }
        });
    }
}