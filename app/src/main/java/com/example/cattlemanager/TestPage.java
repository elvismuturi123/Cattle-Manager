package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cattlemanager.Classses.Milk;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
public class TestPage extends AppCompatActivity {
ArrayList<Milk> milkArrayList;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

//        date = findViewById(R.id.disp_milkingDate1);
//        morning = findViewById(R.id.disp_morningTotal1);
//        afternoon = findViewById(R.id.disp_afternoonTotal1);
//        evening = findViewById(R.id.disp_eveningTotal1);
//        total_milk = findViewById(R.id.disp_totalMilk1);
//        milk_notes = findViewById(R.id.disp_milkNotes1);
//        cattleTag = findViewById(R.id.disp_TagNumber2);

//        String milkDay_id = getIntent().getStringExtra("milkDay_ID");
//        DatabaseReference milkRef = FirebaseDatabase.getInstance().getReference("Milk_Details").ch


tableLayout = findViewById(R.id.tableLayout);

fetchMilkData();
    }
    private void fetchMilkData() {
        DatabaseReference milkDataRef = FirebaseDatabase.getInstance().getReference("Milk_Details");

        // Fetch data for the past 7 days
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);
        Date startDate = calendar.getTime();

        Query query = milkDataRef.orderByKey().startAt(startDate.toString()).endAt(ServerValue.TIMESTAMP.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Milk> milkList = new ArrayList<>();
                for (DataSnapshot daySnapshot : snapshot.getChildren()){
                    String date = daySnapshot.getKey();
                    for (DataSnapshot cattleSnapshot : daySnapshot.getChildren()){
                        String cattleName = cattleSnapshot.getKey();
                        Double morningTotal = cattleSnapshot.child("morning_Total").getValue(Double.class);
                        Double afternoonTotal = cattleSnapshot.child("afternoon_Total").getValue(Double.class);
                        Double eveningTotal = cattleSnapshot.child("evening_Total").getValue(Double.class);
                        Double milk_Total = cattleSnapshot.child("milk_Total").getValue(Double.class);

                        Milk milk = cattleSnapshot.getValue(Milk.class);
                        milkList.add(milk);
                    }
                }
                populateTable(milkList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toolbox.showToast(TestPage.this, "Error!!!!!!!!!!!!!!");
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void populateTable(List<Milk> milkList){
        TableRow headerRow = new TableRow(this);
        TextView cattleNameHeader = new TextView(this);
        cattleNameHeader.setText("Cattle_Name");
        TextView cattleMorning = new TextView(this);
        cattleMorning.setText("Morning_Total");
        TextView cattleAfternoon = new TextView(this);
        cattleAfternoon.setText("Afternoon_Total");
        TextView cattleEvening = new TextView(this);
        cattleEvening.setText("Evening_Total");
        TextView cattleTotal = new TextView(this);
        cattleTotal.setText("Total_Milk");

//  set layout parameters to be used.
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT
        );
        headerRow.setLayoutParams(rowParams);

        TableRow.LayoutParams textParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT
        );
        cattleNameHeader.setLayoutParams(textParams);
        cattleMorning.setLayoutParams(textParams);
        cattleAfternoon.setLayoutParams(textParams);
        cattleEvening.setLayoutParams(textParams);
        cattleTotal.setLayoutParams(textParams);

        tableLayout.addView(headerRow);

        // Add headers to the header row
        headerRow.addView(cattleNameHeader);
        headerRow.addView(cattleMorning);
        headerRow.addView(cattleAfternoon);
        headerRow.addView(cattleEvening);
        headerRow.addView(cattleTotal);

        for (Milk milk : milkList){
            TableRow dataRow = new TableRow(this);

            TextView cattleTag = new TextView(this);
            cattleTag.setText(milk.getCattleIdentifier());

            TextView CattleMorning = new TextView(this);
            CattleMorning.setText(String.valueOf(milk.getMorning_Total()));

            TextView CattleAfternoon = new TextView(this);
            CattleAfternoon.setText(String.valueOf(milk.getAfternoon_Total()));

            TextView CattleEvening = new TextView(this);
            CattleEvening.setText(String.valueOf(milk.getEvening_Total()));

            TextView CattleTotal = new TextView(this);
            CattleTotal.setText(String.valueOf(milk.getMilk_Total()));


            // Set layout parameters for data cells
            TableRow.LayoutParams textParams1 = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            dataRow.addView(cattleTag);
            dataRow.addView(cattleMorning);
            dataRow.addView(cattleAfternoon);
            dataRow.addView(cattleEvening);
            dataRow.addView(cattleTotal);

            tableLayout.addView(dataRow);




        }
    }
}