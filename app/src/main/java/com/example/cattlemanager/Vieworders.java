package com.example.cattlemanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Adapters.OrderAdapter;
import com.example.cattlemanager.Classses.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Vieworders extends AppCompatActivity {

    OrderAdapter orderAdapter;
    RecyclerView ordersRecyclerView;

    ArrayList<Order> retrievedOrderArrayList;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    DatabaseReference ordersRef  = firebaseDatabase.getReference("Orders");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieworders);

        ordersRecyclerView = findViewById(R.id.showOrdersRecycler);
        retrievedOrderArrayList = new ArrayList<>();


        ordersEventListener();

    }

    private void ordersEventListener() {
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //clear existing values

                retrievedOrderArrayList.clear();


                if (snapshot.exists()){
                   Iterable<DataSnapshot> children = snapshot.getChildren();



                   for (DataSnapshot child: children){
                       String childKey =child.getKey();

                       Order retreived_order = child.getValue(Order.class);
                       retrievedOrderArrayList.add(retreived_order);

                       orderAdapter = new OrderAdapter(getApplicationContext(),retrievedOrderArrayList);
                       ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                       ordersRecyclerView.setAdapter(orderAdapter);
                       orderAdapter.notifyDataSetChanged();

                   }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
