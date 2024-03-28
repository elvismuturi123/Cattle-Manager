package com.example.cattlemanager.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cattlemanager.Classses.MilkProduct;
import com.example.cattlemanager.Classses.Toolbox;
import com.example.cattlemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Guernsey extends Fragment {
    DatabaseReference ProductGuernseyRef;
    TextView dispBreedName, dispAvailableQuantity, dispPricePerLitre;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guernsey, container, true);

        dispBreedName = view.findViewById(R.id.dispBreedSales);
        dispAvailableQuantity = view.findViewById(R.id.dispAvailableQuantity);
        dispPricePerLitre = view.findViewById(R.id.disp_pricePerLitre);

        // Get data from Sales database
         ProductGuernseyRef = FirebaseDatabase.getInstance().getReference("Sales").child("Guernsey");
        ProductGuernseyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Handle successful retrieval
                Toolbox.showToast(getContext(), "Successful Retrieval");
                if (snapshot.exists()) {

                    ArrayList<MilkProduct> milkData = new ArrayList<MilkProduct>();

                    MilkProduct milkProduct = null;
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        milkProduct = childSnapshot.getValue(MilkProduct.class);
                        milkData.add(milkProduct);

                    }
                    assert milkProduct != null;
                    updateGuernseyData(milkProduct);

                } else {
                    Toolbox.showToast(getContext(), "No data is found ");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toolbox.showToast(getContext(), error.getMessage());
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private void updateGuernseyData(MilkProduct milkProduct){

        if (milkProduct.getcBreed().equals("Guernsey") ) {
            dispBreedName.setText(milkProduct.getcBreed());
            dispAvailableQuantity.setText("Guernsey"); // Assuming MilkProduct has a getcTotal() method
            dispPricePerLitre.setText(String.valueOf(milkProduct.getcPrice())); // Assuming MilkProduct has a getPricePerLiter() method

        }
    }
}