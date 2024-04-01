package com.example.cattlemanager;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.cattlemanager.Classses.MilkProduct;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMilkToSell extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener listener;
    String productID = " ";
    private AppCompatButton saveSalesButton;
    String cow_Breed = " ";
    Spinner cowBreedCategorySpinner;
    private EditText selectDate;
    private EditText availableMilk;
    private EditText pricePerLitre;
    private EditText sales_Notes;

    DatabaseReference milkProductsRef;

    ArrayAdapter<String> CowBreedCategoryArrayAdapter;
    String[] cow_BreedCategories_array;

    String saleID, breed, date, available, note, price;
    boolean isEditMode = false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_milk_to_sell);

        saveSalesButton = findViewById(R.id.save_mButton);
        cowBreedCategorySpinner = findViewById(R.id.cattleBreedSelectSpinner);
        selectDate = findViewById(R.id.sale_date);
        availableMilk = findViewById(R.id.available_total);
        pricePerLitre = findViewById(R.id.pricePerLitre);
        sales_Notes = findViewById(R.id.Sale_notes_id);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");


        cow_BreedCategories_array = new String[5];
        String Breed1 = "Ayrshire";
        String Breed2 = "Jersey";
        String Breed3 = "Guernsey";
        String Breed4 = "Holstein";
        String Breed5 = "Fresian";

        cow_BreedCategories_array[0] = Breed1;
        cow_BreedCategories_array[1] = Breed2;
        cow_BreedCategories_array[2] = Breed3;
        cow_BreedCategories_array[3] = Breed4;
        cow_BreedCategories_array[4] = Breed5;

        // Initialize cowBreedCategorySpinner by finding it using findViewById
        cowBreedCategorySpinner = findViewById(R.id.cattleBreedSelectSpinner); // Replace 'your_spinner_id' with the actual ID of your spinner

        // Check if cowBreedCategorySpinner is not null before setting its adapter
        if (cowBreedCategorySpinner != null) {
            //  Toolbox.showToast(AddMilkToSell.this, "Successful Operation");
            CowBreedCategoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cow_BreedCategories_array);
            CowBreedCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cowBreedCategorySpinner.setAdapter(CowBreedCategoryArrayAdapter);

            cowBreedCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cow_Breed = cow_BreedCategories_array[position];
                    //  Toolbox.showToast(AddMilkToSell.this, "Selected Breed is: " + cow_Breed);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle when no item is selected
                    Toolbox.showToast(getApplicationContext(), "Error Occurred!!!!");
                }
            });
        } else {
            // Handle the case if cowBreedCategorySpinner is null, maybe log an error message
            Log.e("YourActivity", "Cow Breed Spinner is null!");
        }




        //get data from intent

        saleID = getIntent().getStringExtra("saleId");
        breed = getIntent().getStringExtra("breed");
        date = getIntent().getStringExtra("date");
        available = getIntent().getStringExtra("available");
        note = getIntent().getStringExtra("notes");
        price = getIntent().getStringExtra("price");
        isEditMode = getIntent().getBooleanExtra("editMode", false);

        if (isEditMode) {
            // we are in edit mode
            selectDate.setText(date);
            availableMilk.setText(available);
            pricePerLitre.setText(price);
            sales_Notes.setText(note);

            //Toolbox.showToast(getApplicationContext(),breed);
            Toolbox.showToast(getApplicationContext(), "IS EDIT MODE");


            int position = CowBreedCategoryArrayAdapter.getPosition(breed);
            cowBreedCategorySpinner.setSelection(position);


        }


        // Create database
        milkProductsRef = FirebaseDatabase.getInstance().getReference("Products");


        // Spinner function called.
        //setUpBreedCategorySpinner();


        // SAVE DATA TO THE DATABASE
        saveSalesButton.setOnClickListener(v -> {


            String dbDate = selectDate.getText().toString();
            String dbMilk = availableMilk.getText().toString();
            String dbPrice = pricePerLitre.getText().toString();
            String dbNotes = sales_Notes.getText().toString();

            if(isEditMode){
                //update existing data

//                int existingMilk, newMilk, totalAvailableMilk;
//                existingMilk = Integer.valueOf(available);
//                newMilk = Integer.valueOf(dbMilk);
//
//                totalAvailableMilk =existingMilk+newMilk;


                Map<String ,Object> milkUpdates = new HashMap<>();
                milkUpdates.put("productID",saleID);
                milkUpdates.put("cBreed",breed);
                milkUpdates.put("cDate",dbDate);
                milkUpdates.put("cTotal",dbMilk);
                milkUpdates.put("cPrice",dbPrice);
                milkUpdates.put("cNotes",dbNotes);

                databaseReference.child(saleID).updateChildren(milkUpdates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error==null){
                            //update succeeded
                            Toolbox.showToast(getApplicationContext(),"Update Successful");
                            Toolbox.navigateTo(getApplicationContext(), Display_SalesProducts.class);
                        }else {
                            //update failed
                            Toolbox.showToast(getApplicationContext(),error.toException().toString());
                        }
                    }
                });

            }else {

                //create a new record
                DatabaseReference newProductRecord = milkProductsRef.push();
                String autogeneratedRcId = newProductRecord.getKey();
                productID = autogeneratedRcId;

                //check for an existing record

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Products");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean existingRecordFound = false;

                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String cowBreed = (String) child.child("cBreed").getValue();
                                Toolbox.showToast(getApplicationContext(), " " + cowBreed);

                                if (cow_Breed.equals(cowBreed)) {
                                    existingRecordFound = true;
                                    Toolbox.showToast(getApplicationContext(), "There is an existing record for this breed. Please update the record");
                                    break; // No need to continue, we found an existing record
                                }

                            }
                        }



                        if (!existingRecordFound) {
                            // create a new record
                            MilkProduct new_record = new MilkProduct(productID, cow_Breed, dbDate, dbMilk, dbPrice, dbNotes);
                            newProductRecord.setValue(new_record, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if (error != null) {
                                        Toolbox.showToast(getApplicationContext(), error.getMessage());
                                    } else {
                                        Toolbox.showToast(getApplicationContext(), "Data saved successfully!!!");
                                        Toolbox.navigateTo(getApplicationContext(), Display_SalesProducts.class);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle errors
                    }
                });

            }



        });


        availableMilk.setInputType(InputType.TYPE_CLASS_NUMBER);
        pricePerLitre.setInputType(InputType.TYPE_CLASS_NUMBER);


        // SETUP DATE SELECTION
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMilkToSell.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (DatePickerDialog.OnDateSetListener) listener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                datePickerDialog.show();
            }
        });
        listener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                selectDate.setText(date);
            }
        };
    }







}