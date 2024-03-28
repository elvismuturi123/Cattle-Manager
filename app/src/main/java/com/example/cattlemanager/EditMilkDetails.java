package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cattlemanager.Classses.Milk;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditMilkDetails extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener listener;
    TextView textView;
    String milkDate_id;
    EditText milking_cDate;
    EditText morning_cTotal;
    EditText afternoon_cTotal;
    EditText evening_cTotal;
    EditText total_cMilk;
    EditText milk_cNotes;
    AppCompatButton save_Records;
    AppCompatButton edit_Records;
    AppCompatButton delete_Records;
    DatabaseReference milkDbRef;
    private TextView tagNO;
    private String selectedCattleId;
    String dayId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_milk_details);

        milking_cDate = findViewById(R.id.milking_date);
        morning_cTotal = findViewById(R.id.morning_total);
        afternoon_cTotal = findViewById(R.id.afternoon_total);
        evening_cTotal = findViewById(R.id.evening_total);
        total_cMilk = findViewById(R.id.milk_total);
        milk_cNotes = findViewById(R.id.Milk_notes_id);
        save_Records = findViewById(R.id.save_mButton);
        tagNO = findViewById(R.id.cattleSelectSpinner);
        delete_Records = findViewById(R.id.deleterecordBtn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Milk_Details");

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
                try {

                    morningValue = Double.parseDouble(morning_cTotal.getText().toString());
                    afternoonValue = Double.parseDouble(afternoon_cTotal.getText().toString());
                    eveningValue = Double.parseDouble(evening_cTotal.getText().toString());

                } catch (NumberFormatException e) {
                    //  To handle invalid input if needed
                }
                double totalMilk = morningValue + afternoonValue + eveningValue;
                total_cMilk.setText(String.valueOf(totalMilk));
            }
        };
        morning_cTotal.addTextChangedListener(textWatcher);
        afternoon_cTotal.addTextChangedListener(textWatcher);
        evening_cTotal.addTextChangedListener(textWatcher);

        //get milk details

        Milk milkRecord = getIntent().getParcelableExtra("milk");

//        add data to  widget
        String tagNo = milkRecord.getCattleIdentifier();
        String milking_date = milkRecord.getMilking_Date();
        Double morning_total = milkRecord.getMorning_Total();
        Double afternoon_total = milkRecord.getAfternoon_Total();
        Double evening_total = milkRecord.getEvening_Total();
        Double milk_total = milkRecord.getMilk_Total();
        String desription = milkRecord.getMilk_Notes();
        dayId = milkRecord.getMilkDay_id();

        tagNO.setText(tagNo);
        milking_cDate.setText(milking_date);
        morning_cTotal.setText(morning_total.toString());
        afternoon_cTotal.setText(afternoon_total.toString());
        evening_cTotal.setText(evening_total.toString());
        total_cMilk.setText(milk_total.toString());
        milk_cNotes.setText(desription);

        // firebase to update the record
        save_Records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagNo = tagNO.getText().toString();
                String milking_date = milking_cDate.getText().toString();
                Double morning_total = Double.valueOf(morning_cTotal.getText().toString());
                Double afternoon_total = Double.valueOf(afternoon_cTotal.getText().toString());
                Double evening_total = Double.valueOf(evening_cTotal.getText().toString());
                Double milk_total = Double.valueOf(total_cMilk.getText().toString());
                String desription = milk_cNotes.getText().toString();


                Map<String, Object> updatedMapRecord = new HashMap<>();
                updatedMapRecord.put("cattleIdentifier", tagNo);
                updatedMapRecord.put("milkDay_id", dayId);
                updatedMapRecord.put("milking_Date", milking_date);
                updatedMapRecord.put("morning_Total", morning_total);
                updatedMapRecord.put("afternoon_Total", afternoon_total);
                updatedMapRecord.put("evening_Total", evening_total);
                updatedMapRecord.put("milk_Total", milk_total);
                updatedMapRecord.put("milk_Notes", desription);

                Log.d(": uPDtedRecord", "onClick:" + updatedMapRecord.toString());


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(dayId).updateChildren(updatedMapRecord);
                        Toast.makeText(EditMilkDetails.this, "Record Updated", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Display_milk.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditMilkDetails.this, "Record Update failed" + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        morning_cTotal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        afternoon_cTotal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        evening_cTotal.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        total_cMilk.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);



        // (Button) Deleting The Record with the data
        delete_Records.setOnClickListener(v -> {

            showAlertDialog();

            
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Milk Record");
        builder.setMessage("Are you sure you want to delete this milk record? This action cannot be reversed!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle positive button click here
                // user wants to delete the record.Proceed with deletion

                deleteMilkRecord();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click here (optional)
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void deleteMilkRecord() {

        DatabaseReference milkDateRecordRef = databaseReference.child(dayId); // Assuming you have the milkDay_id variable
        milkDateRecordRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Record Deleted Successfully !!!!!", Toast.LENGTH_SHORT).show();
                Toolbox.navigateTo(this, Display_milk.class);
            } else {
                // Handle error
                Toast.makeText(this, "Error deleting record", Toast.LENGTH_SHORT).show();
                // Log the error for debugging
            }
        });
        
    }
}