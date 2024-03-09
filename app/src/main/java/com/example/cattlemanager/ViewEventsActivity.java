package com.example.cattlemanager;

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

public class ViewEventsActivity extends AppCompatActivity {

//    FloatingActionButton navToAddEvents;
    Button logout;

    RecyclerView recyclerViewEventRec;

    ArrayList<Events> eventsArrayList;
    EventAdapter eventAdapter;

    DatabaseReference eventDb = FirebaseDatabase.getInstance().getReference();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        FloatingActionButton navToAddEvents = findViewById(R.id.navToAddEvents);
        logout = findViewById(R.id.logoutFromDispEvents);
        recyclerViewEventRec = findViewById(R.id.recyclerViewEvents);

        eventsArrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Events_Details");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Events events =  dataSnapshot.getValue(Events.class);

                    eventsArrayList.add(events);
                }
                eventAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerViewEventRec.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(this, eventsArrayList);
        recyclerViewEventRec.setAdapter(eventAdapter);

        navToAddEvents.setOnClickListener(v ->{
            Toolbox.navigateTo(ViewEventsActivity.this, AddEvents.class);
        });
        logout.setOnClickListener(v ->{
            Toolbox.navigateTo(ViewEventsActivity.this, Login.class);
        });


    }
}