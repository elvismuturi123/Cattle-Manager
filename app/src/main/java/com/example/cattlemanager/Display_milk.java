package com.example.cattlemanager;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Adapters.MilkAdapter;
import com.example.cattlemanager.Classses.Milk;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_milk extends AppCompatActivity {

   ImageButton navigate_mRecords;
    RecyclerView recyclerViewMilkRec;
    ImageButton menuButton;
    ArrayList<Milk> milkArrayList;
    MilkAdapter milkAdapter;
    DatabaseReference milkDb = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_milk);

        recyclerViewMilkRec = findViewById(R.id.mRecords_RecyclerV);

         navigate_mRecords = findViewById(R.id.navTocMilkRecButton);

         milkArrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Milk_Details");

        databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        milkArrayList.clear();

        for (DataSnapshot dataSnapshot: snapshot.getChildren()){

            Milk milk =  dataSnapshot.getValue(Milk.class);
                milkArrayList.add(milk);
        }
        milkAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        recyclerViewMilkRec.setLayoutManager(new LinearLayoutManager(this));
        milkAdapter = new MilkAdapter(this, milkArrayList);
        recyclerViewMilkRec.setAdapter(milkAdapter);

        navigate_mRecords.setOnClickListener(v -> Toolbox.navigateTo(Display_milk.this, Milk_details.class));

    }
    void setupRecyclerView(){
    }

    @Override
    public void finish() {
        super.finish();
    }
}