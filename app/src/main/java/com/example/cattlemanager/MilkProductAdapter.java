package com.example.cattlemanager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MilkProductAdapter extends RecyclerView.Adapter<MilkProductAdapter.MyViewHolder> {

    Context context;
    ArrayList<Milk> milkList;

    public MilkProductAdapter(Context context, ArrayList milkList) {
        this.context = context;
        this.milkList = milkList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_milkproduct_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Milk retrievedMilk = milkList.get(position);


        holder.dispBreedName.setText(retrievedMilk.getCow_breed());
        holder.dispAvailableQuantity.setText(String.valueOf((int) retrievedMilk.getMilk_Total()));
        holder.dispPricePerLiter.setText("KES 70.00");



        holder.btn_buyMilk.setOnClickListener(v -> {

          //  Intent intent = new Intent(context, ViewAllCattleDetails.class);

           //context.startActivity(intent);

            Toolbox.showToast(context.getApplicationContext(), "Button Clicked");

        });

    }

    @Override
    public int getItemCount() {
        return milkList == null ? 0 : milkList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dispBreedName, dispAvailableQuantity, dispPricePerLiter, dispCurrency;

        Button btn_buyMilk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dispBreedName = itemView.findViewById(R.id.disp_breedName);
            dispAvailableQuantity = itemView.findViewById(R.id.disp_availableQuantity);
            dispCurrency = itemView.findViewById(R.id.disp_currency);
            dispPricePerLiter = itemView.findViewById(R.id.disp_pricePerLitre);
            btn_buyMilk = itemView.findViewById(R.id.btn_buyMilk);

        }
    }


}
