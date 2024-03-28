package com.example.cattlemanager;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Adapters.SalesAdapter;
import com.example.cattlemanager.Classses.MilkProduct;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_SalesProducts extends AppCompatActivity {
    RecyclerView recyclerViewProducts;
    ArrayList <MilkProduct> productsArrayList;
    SalesAdapter salesAdapter;
    DatabaseReference databaseReference;
    FloatingActionButton navToAddSale;
    Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sales_products);

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        navToAddSale = findViewById(R.id.navTocAddSalesButton);
        logout = findViewById(R.id.logoutFromDispProducts);

        productsArrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsArrayList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    MilkProduct newSaleRec =  dataSnapshot.getValue(MilkProduct.class);
                    productsArrayList.add(newSaleRec);
                }
                salesAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        salesAdapter = new SalesAdapter(this, productsArrayList);
        recyclerViewProducts.setAdapter(salesAdapter);
        navToAddSale.setOnClickListener(v -> Toolbox.navigateTo(Display_SalesProducts.this, AddMilkToSell.class));

    }
    @Override
    public void finish() {
        super.finish();
    }
}