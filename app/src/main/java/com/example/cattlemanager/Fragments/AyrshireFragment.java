package com.example.cattlemanager.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cattlemanager.Classses.MilkProduct;
import com.example.cattlemanager.Classses.Order;
import com.example.cattlemanager.Classses.Toolbox;
import com.example.cattlemanager.R;
import com.example.cattlemanager.SalesPageActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AyrshireFragment extends Fragment {
    DatabaseReference AyrshireProductRef;
    TextView dispBreedName2, dispAvailableQuantity2, dispPricePerLitre2;
    String productID2, breed2, date2, available2, note2, price2;
    DatabaseReference ordersRef2;
    Button btn_buyMilk2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ayrshire, container,true);

        dispBreedName2 = view.findViewById(R.id.dispBreedSales);
        dispAvailableQuantity2 = view.findViewById(R.id.dispAvailableQuantity);
        dispPricePerLitre2 = view.findViewById(R.id.disp_pricePerLitre);
        btn_buyMilk2 = view.findViewById(R.id.buyButton);

        ordersRef2 =  FirebaseDatabase.getInstance().getReference("Orders");
        // Get a reference to the database location
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Iterate through the snapshot to access all child nodes and their data
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    Object value = childSnapshot.getValue();

                    MilkProduct milkProducts = childSnapshot.getValue(MilkProduct.class);

                    if (milkProducts!=null){

                        if(milkProducts.getcBreed().equals("Ayrshire")){

                            if (!isAdded()){

                            }else {
                                Toolbox.showToast(getActivity(),"match found");
                            }

                            breed2 = milkProducts.getcBreed();
                            available2 =milkProducts.getcTotal();
                            price2 =milkProducts.getcPrice();
                            productID2 =milkProducts.getProductID();
                        }
                    }
                    // Process the key and value as needed
                }
                dispBreedName2.setText(breed2);
                dispAvailableQuantity2.setText(available2);
                dispPricePerLitre2.setText(price2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });

        // Button Proceed
        btn_buyMilk2.setOnClickListener(v -> {
            //show buy milk dialog

            LayoutInflater buyMilkInflater = LayoutInflater.from(getActivity());
            View customView = buyMilkInflater.inflate(R.layout.layout_buymilk, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Set custom view
            builder.setView(customView);

            final AlertDialog buyMilkAlertDialog = builder.create();

            buyMilkAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.style_roundedcorners_bg);

            // Find views in the custom layout
            TextView alertTitle = customView.findViewById(R.id.titleTextView);
            EditText Quantity = customView.findViewById(R.id.milkQuantity);
            Button btn_cancel = customView.findViewById(R.id.btnCancel);
            Button btn_proceed = customView.findViewById(R.id.btnProceed);
            String title, message, warning;
            title = "BUY MILK";

            alertTitle.setText(title);

            btn_cancel.setOnClickListener(view1 -> {

                buyMilkAlertDialog.dismiss();

            });

            btn_proceed.setOnClickListener(view2 -> {
                //place order

                DatabaseReference newMilkOrderRecord = ordersRef2.push();
                String orderID = newMilkOrderRecord.getKey();

                String quantityEntered = Quantity.getText().toString().trim();

                String  orderUniqueCode = "DKF"+Toolbox.generateRandomNumber(4);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String order_date = dateFormat.format(currentDate);

                double totalOrderAmount = Double.valueOf(quantityEntered)*Double.valueOf(price2);


                //create a  new order
                Order new_order = new Order(orderID,order_date,quantityEntered," ",orderUniqueCode,"NOT PROCESSED",Toolbox.timestamp2_String(Timestamp.now()),productID2,totalOrderAmount);
                newMilkOrderRecord.setValue(new_order, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error==null){
                            //update succeeded
                            Toolbox.showToast(getActivity(),"Order placed successfully !!!!");
                            Toolbox.navigateTo(getActivity(), SalesPageActivity.class);
                        }else {
                            //update failed
                            Toolbox.showToast(getActivity(),error.toException().toString());
                        }
                    }
                });

                Toolbox.showToast(getActivity(),quantityEntered);

                buyMilkAlertDialog.dismiss();
            });

            // Create and show the dialog
            buyMilkAlertDialog.show();

        });



        // Inflate the layout for this fragment
        return view;
    }
}