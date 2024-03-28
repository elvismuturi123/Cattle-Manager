package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cattlemanager.Adapters.MilkAdapter;
import com.example.cattlemanager.Classses.Milk;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class View_all_milk_details extends AppCompatActivity {

    ArrayList<Milk> milkArrayList;
    RecyclerView recyclerViewAllmDetails;
    FirebaseDatabase db_milk = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    MilkAdapter milkAdapter;
    TextView milking_date;
    TextView morning_total;
    TextView afternoon_total;
    TextView evening_total;
    TextView total_milk;
    TextView milk_notes;
    TextView cattleIdentifier;
    AppCompatButton navigateToCare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_milk_details);

         milking_date = findViewById(R.id.disp_milkingDate);
         morning_total = findViewById(R.id.disp_morningTotal);
         afternoon_total = findViewById(R.id.disp_afternoonTotal);
         evening_total = findViewById(R.id.disp_eveningTotal);
         total_milk = findViewById(R.id.disp_totalMilk);
         milk_notes = findViewById(R.id.disp_milkNotes);
        cattleIdentifier = findViewById(R.id.disp_TagNumber);
        navigateToCare = findViewById(R.id.navigateToCare);

         String milkDay_id = getIntent().getStringExtra("milkDay_ID");
        DatabaseReference docRef1 = FirebaseDatabase.getInstance().getReference("Milk_Details").child(milkDay_id);
            docRef1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()) {
                        DataSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            // Document exists and has data
                            //  Object documentData = snapshot.getValue();
                            // Process the retrieved data here
                            Milk retrieved_milk_data =snapshot.getValue(Milk.class);
                            cattleIdentifier.setText(retrieved_milk_data.getCattleIdentifier());
                            milking_date.setText(retrieved_milk_data.getMilking_Date());
                            morning_total.setText(String.valueOf(retrieved_milk_data.getMorning_Total()));
                            afternoon_total.setText(String.valueOf(retrieved_milk_data.getAfternoon_Total()));
                            evening_total.setText(String.valueOf(retrieved_milk_data.getEvening_Total()));
                            total_milk.setText(String.valueOf(retrieved_milk_data.getMilk_Total()));
                            milk_notes.setText(retrieved_milk_data.getMilk_Notes());
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
            navigateToCare.setOnClickListener(v -> {
                Toolbox.navigateTo(getApplicationContext(), AddEvents.class);
                finish();
            });
    }

}