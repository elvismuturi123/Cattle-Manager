package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_cattle extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    ArrayList<Cows> cowsList ;

    RecyclerView recyclerViewCattle;

    DatabaseReference databaseReference;
    CattleAdapter cattleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cattle);

        recyclerViewCattle = findViewById(R.id.recyclerViewCattle);
        FloatingActionButton AddcRecords = findViewById(R.id.navTocRecButton);
        Button logout1 = findViewById(R.id.logoutFromDispCattle);
        cowsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("cattle_details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Cows cows =  dataSnapshot.getValue(Cows.class);

                    cowsList.add(cows);
                }
                cattleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        recyclerViewCattle.setLayoutManager(new LinearLayoutManager(this));
        cattleAdapter = new CattleAdapter(this, cowsList);
        recyclerViewCattle.setAdapter(cattleAdapter);
        // ... (rest of your code)
        logout1.setOnClickListener((v) -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(Display_cattle.this, "You are logged out!!", Toast.LENGTH_LONG).show();

            startActivity(new Intent(Display_cattle.this, Login.class));
            finish();
        });
        AddcRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.navigateTo(Display_cattle.this, CRecords.class);
            }
        });
    }
}
