package com.example.cattlemanager.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.R;
import com.example.cattlemanager.Classses.MilkProduct;
import com.example.cattlemanager.ViewAllProducts;

import java.util.ArrayList;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {

    Context context;
    ArrayList <MilkProduct> retrievedMilkProductArraylist;
    public SalesAdapter(Context context, ArrayList retrievedSalesArraylist) {
        this.context = context;
        this.retrievedMilkProductArraylist = retrievedSalesArraylist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_item_view, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MilkProduct newMilkProductRecord = retrievedMilkProductArraylist.get(position);
        holder.Breed.setText(newMilkProductRecord.getcBreed());
        holder.date.setText(newMilkProductRecord.getcDate());
        holder.available_Milk.setText(newMilkProductRecord.getcTotal());
        holder.PricePerLitre.setText(newMilkProductRecord.getcPrice());
        String sale_id = newMilkProductRecord.getProductID();

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, ViewAllProducts.class);
            intent.putExtra("saleId", sale_id);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return retrievedMilkProductArraylist == null ? 0 : retrievedMilkProductArraylist.size();
    }
    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView Breed, date, available_Milk,PricePerLitre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Breed = itemView.findViewById(R.id.disp_Breed);
            date = itemView.findViewById(R.id.disp_saleDate);
            available_Milk = itemView.findViewById(R.id.disp_milkAvailable);
            PricePerLitre = itemView.findViewById(R.id.disp_pricePerLter);

        }
    }


}
