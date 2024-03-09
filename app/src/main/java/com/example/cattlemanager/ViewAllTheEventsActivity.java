package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewAllTheEventsActivity extends AppCompatActivity {

    ArrayList<Events> eventsArrayList;
    RecyclerView recyclerViewAllEventsDetails;
    FirebaseDatabase db_events = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    EventAdapter eventAdapter;
    TextView event_date;
    TextView event_type;
    TextView cSymptoms;
    TextView CDiagnosis;
    TextView CTechnician;
    TextView cEventNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_the_events);

        event_date = findViewById(R.id.disp_EventDate);
        event_type = findViewById(R.id.disp_EventType);
        cSymptoms = findViewById(R.id.disp_Symptoms);
        CDiagnosis = findViewById(R.id.disp_Diagnosis);
        CTechnician = findViewById(R.id.disp_Technician);
        cEventNotes = findViewById(R.id.disp_eventNotes);

        String eventDay_id = getIntent().getStringExtra("Event_dayId");
        DatabaseReference docRef1 = FirebaseDatabase.getInstance().getReference("Events_Details").child(eventDay_id);
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        // Document exists and has data
                        // Process the retrieved data here
                        Events retrieved_events_details =snapshot.getValue(Events.class);
                        event_date.setText(retrieved_events_details.getEvent_Date());
                        event_type.setText(retrieved_events_details.getEvent_Type());
                        cSymptoms.setText(retrieved_events_details.getSymptoms());
                        CDiagnosis.setText(retrieved_events_details.getDiagnosis());
                        CTechnician.setText(retrieved_events_details.getTechnician());
                        cEventNotes.setText(retrieved_events_details.getEvent_Notes());
                    } else {
                        // Document does not exist
                        Log.d("TAG", "Document not found");
                    }
                } else {
                    // Handle errors
                    Log.d("TAG", "Error getting document", task.getException());
                }
            }
        });
    }
}