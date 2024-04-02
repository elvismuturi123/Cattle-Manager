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

public class FresianFragment extends Fragment {

    DatabaseReference ProductGuernseyRef;
    TextView dispBreedName, dispAvailableQuantity, dispPricePerLitre;

    String productID, breed, date, available, note, price;

    DatabaseReference ordersRef;
    Button btn_buyMilk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friesian, container, false);

        dispBreedName = view.findViewById(R.id.dispBreedSales);
        dispAvailableQuantity = view.findViewById(R.id.dispAvailableQuantity);
        dispPricePerLitre = view.findViewById(R.id.disp_pricePerLitre);
        btn_buyMilk = view.findViewById(R.id.buyButton);

        ordersRef =  FirebaseDatabase.getInstance().getReference("Orders");

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

                        if(milkProducts.getcBreed().equals("Fresian")){

                            if (!isAdded()){

                            }else {
                            }

                            breed = milkProducts.getcBreed();
                            available =milkProducts.getcTotal();
                            price =milkProducts.getcPrice();
                            productID =milkProducts.getProductID();
                        }
                    }
                    // Process the key and value as needed
                }
                dispBreedName.setText(breed);
                dispAvailableQuantity.setText(available);
                dispPricePerLitre.setText(price);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });

        btn_buyMilk.setOnClickListener(v -> {
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
            title = "Buy Milk";

            alertTitle.setText(title);

            btn_cancel.setOnClickListener(view1 -> {

                buyMilkAlertDialog.dismiss();

            });

            btn_proceed.setOnClickListener(view2 -> {
                //place order

                DatabaseReference newMilkOrderRecord = ordersRef.push();
                String orderID = newMilkOrderRecord.getKey();


                String quantityEntered = Quantity.getText().toString().trim();

                String  orderUniqueCode = "DKF"+Toolbox.generateRandomNumber(4);

                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String order_date = dateFormat.format(currentDate);

                double totalOrderAmount = Double.valueOf(quantityEntered)*Double.valueOf(price);



                //create a  new order
                Order new_order = new Order(orderID,order_date,quantityEntered," ",orderUniqueCode,"NOT PROCESSED",Toolbox.timestamp2_String(Timestamp.now()),productID,totalOrderAmount);

                newMilkOrderRecord.setValue(new_order, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error==null){
                            //update succeeded
                            Toolbox.showToast(getActivity(),"Order placed successfully");
                            Toolbox.navigateTo(getContext(), SalesPageActivity.class);
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

        return view;
    }
}