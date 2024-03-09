package com.example.cattlemanager;

import static com.example.cattlemanager.R.id.feedRecords_RecyclerV;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_Feed_Program extends AppCompatActivity {

    //ImageButton navigate_mRecords;
    RecyclerView recyclerViewFeedProgram;
    //ImageButton menuButton;
    ArrayList<Feed> feedProgramArrayList;
    FeedAdapter feedAdapter;

    DatabaseReference feedProgramDb = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference;

    Button LogOutFromDispFeedProgram;
    FloatingActionButton AddNewFeedingProgram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed_program);

        recyclerViewFeedProgram = findViewById(feedRecords_RecyclerV);


        feedProgramArrayList = new ArrayList<>();

        LogOutFromDispFeedProgram = findViewById(R.id.logoutFromDisplayFeedProgram);
        AddNewFeedingProgram = findViewById(R.id.navToAddFeedProgramButton);


        databaseReference = FirebaseDatabase.getInstance().getReference("Feeding_Program");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Feed feed =  dataSnapshot.getValue(Feed.class);

                    feedProgramArrayList.add(feed);
                }
                feedAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerViewFeedProgram.setLayoutManager(new LinearLayoutManager(this));
        feedAdapter = new FeedAdapter(this, feedProgramArrayList);
        recyclerViewFeedProgram.setAdapter(feedAdapter);

        AddNewFeedingProgram.setOnClickListener(v -> Toolbox.navigateTo(View_Feed_Program.this, AddFeedingProgram.class));
        LogOutFromDispFeedProgram.setOnClickListener(v -> Toolbox.navigateTo(View_Feed_Program.this, Login.class));
         }

}