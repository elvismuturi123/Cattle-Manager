package com.example.cattlemanager;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.Cows;
import com.example.cattlemanager.Classses.Milk;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Milk_details extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener listener;
    TextView textView;
    String milkDate_id;
    EditText milking_cDate;
    EditText morning_cTotal;
    EditText afternoon_cTotal;
    EditText evening_cTotal;
    EditText total_cMilk;
    EditText milk_cNotes;
    Button save_Records;
    DatabaseReference milkDbRef;
    Spinner cattleselectSpinner;
    String selectedCattleName;

    List<Cows> cattleList = new ArrayList<>();
    String cowBreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_details);

        milking_cDate = findViewById(R.id.milking_date);
        morning_cTotal = findViewById(R.id.morning_total);
        afternoon_cTotal = findViewById(R.id.afternoon_total);
        evening_cTotal = findViewById(R.id.evening_total);
        total_cMilk = findViewById(R.id.milk_total);
        milk_cNotes = findViewById(R.id.Milk_notes_id);
        save_Records = findViewById(R.id.save_mButton);
        cattleselectSpinner = findViewById(R.id.cattleNameSelectSpinner);

        milkDbRef = FirebaseDatabase.getInstance().getReference().child("Milk_Details");

        //fecth data and put them in spinner

        fetchCattleData();

        // Add total in editTexts
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }

            private void calculateTotal() {
                double morningValue = 0;
                double afternoonValue = 0;
                double eveningValue = 0;


                String morningText = morning_cTotal.getText().toString();
                String afternoonText = afternoon_cTotal.getText().toString();
                String eveningText = evening_cTotal.getText().toString();


                if (!morningText.isEmpty()) {
                    morningValue = Double.parseDouble(morningText);
                } else {
                    // Show an error message or handle the case where morningValue is empty
                    Toolbox.showToast(Milk_details.this, "This field cannot be Empty!!!!!!");
                    return; // Exit the method to avoid further calculations
                }

                if (!afternoonText.isEmpty()) {
                    afternoonValue = Double.parseDouble(afternoonText);
                } else {
                    // Show an error message or handle the case where afternoonValue is empty
                    Toolbox.showToast(Milk_details.this, "This field cannot be Empty!!!!!!");
                    return; // Exit the method to avoid further calculations
                }

                if (!eveningText.isEmpty()) {
                    eveningValue = Double.parseDouble(eveningText);
                } else {
                    // Show an error message or handle the case where eveningValue is empty
                    Toolbox.showToast(Milk_details.this, "This field cannot be Empty!!!!!!");
                    return; // Exit the method to avoid further calculations
                }

                double totalMilk = morningValue + afternoonValue + eveningValue;
                total_cMilk.setText(String.valueOf(totalMilk));

            }
        };
        morning_cTotal.addTextChangedListener(textWatcher);
        afternoon_cTotal.addTextChangedListener(textWatcher);
        evening_cTotal.addTextChangedListener(textWatcher);

        // save data
        save_Records.setOnClickListener(v -> {
            DatabaseReference new_milk_record = milkDbRef.push();
            String autoGeneratedId = new_milk_record.getKey();
            milkDate_id = autoGeneratedId;
            String cattleIdentifier = cattleselectSpinner.getSelectedItem().toString();


            for (Cows selectedCow : cattleList) {
                String cow_name = selectedCow.getCattle_Name();
                String cow_breed = selectedCow.getCow_Breed();

                if(cow_name.equals(cattleIdentifier)){
                    cowBreed =cow_breed;
                    break;
                }

            }


            Toolbox.showToast(getApplicationContext(),"The selected cow breed is: "+cowBreed);

            String milking_Date = milking_cDate.getText().toString();
            double morning_Total = Double.valueOf(morning_cTotal.getText().toString());
            double afternoon_Total = Double.valueOf(afternoon_cTotal.getText().toString());
            double evening_Total = Double.valueOf(evening_cTotal.getText().toString());
            double milk_Total = Double.valueOf(total_cMilk.getText().toString());
            String milk_Notes = milk_cNotes.getText().toString();



            Milk new_milkRec = new Milk(cattleIdentifier,cowBreed, milkDate_id, milking_Date, morning_Total, afternoon_Total, evening_Total, milk_Total, milk_Notes);
            new_milk_record.setValue(new_milkRec).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // data was saved
                    Toolbox.showToast(Milk_details.this, "Data saved successfully");
                    Toolbox.navigateTo(getApplicationContext(), Display_milk.class);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toolbox.showToast(getApplicationContext(), e.getMessage());
                    Log.e("Error Saving Milk", e.getMessage());
                }
            });
        });
// Date picker dialog -------
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        milking_cDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Milk_details.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (DatePickerDialog.OnDateSetListener) listener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                datePickerDialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                milking_cDate.setText(date);
            }
        };
        // Make the keyboard input numbers
        morning_cTotal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        afternoon_cTotal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        evening_cTotal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        total_cMilk.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private void fetchCattleData() {
        DatabaseReference cattleDbRef2 = FirebaseDatabase.getInstance().getReference("cattle_details");
        cattleDbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot cattleSnapshot : snapshot.getChildren()) {
                    Cows cows = cattleSnapshot.getValue(Cows.class);

                    cattleList.add(cows);
                }
                ArrayAdapter<Cows> adapter = new ArrayAdapter<>(Milk_details.this, android.R.layout.simple_spinner_item, cattleList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                cattleselectSpinner.setAdapter(adapter);

                handleSpinnerSelection();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors in the database
                Toolbox.showToast(Milk_details.this, "Error in accessing Database Item!!!!");
            }
        });
    }


    // Spinner selection code
    private void handleSpinnerSelection() {
        cattleselectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cows selectedCowName = (Cows) parent.getItemAtPosition(position);
                selectedCattleName = selectedCowName.getCattle_Name();
                Toolbox.showToast(getApplicationContext(), "Select Cow Name is :  " + selectedCowName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toolbox.showToast(getApplicationContext(), "Error!!!!!!!");
            }
        });
    }
}