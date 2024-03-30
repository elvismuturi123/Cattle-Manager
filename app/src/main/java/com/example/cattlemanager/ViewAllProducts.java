package com.example.cattlemanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.MilkProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewAllProducts extends AppCompatActivity {
    TextView Breed;
    TextView Date;
    TextView AvailableMilk;
    TextView PricePerLitre;

    Button UpdateAvailableMilk;
    String saleID, breed, date, available,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_products);

        //ui declaration
        Breed = findViewById(R.id.disp_Breed1);
        Date = findViewById(R.id.disp_saleDate1);
        AvailableMilk = findViewById(R.id.disp_milkAvailable1);
        PricePerLitre = findViewById(R.id.disp_pricePerLter1);
        UpdateAvailableMilk = findViewById(R.id.updateAvailableMilkBtn);



        //get data from intent

        saleID = getIntent().getStringExtra("saleId");
        breed = getIntent().getStringExtra("breed");
        date = getIntent().getStringExtra("date");
        available = getIntent().getStringExtra("available");
        price = getIntent().getStringExtra("price");


        //set up the ui

        Breed.setText(breed);
        Date.setText(date);
        AvailableMilk.setText(available);
        PricePerLitre.setText(price);




        String SaleId = getIntent().getStringExtra("saleId");
        DatabaseReference docRef1 = FirebaseDatabase.getInstance().getReference("Sales").child("SaleId");
        docRef1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        // Document exists and has data
                        // Process the retrieved data here
                        MilkProduct retrieved_products_details = snapshot.getValue(MilkProduct.class);
                        Breed.setText(retrieved_products_details.getcBreed());
                        Date.setText(retrieved_products_details.getcDate());
                        AvailableMilk.setText(retrieved_products_details.getcTotal());
                        PricePerLitre.setText(retrieved_products_details.getcPrice());

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