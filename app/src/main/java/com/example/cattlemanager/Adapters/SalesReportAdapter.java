package com.example.cattlemanager.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Classses.Sales;
import com.example.cattlemanager.R;

import java.util.ArrayList;

public class SalesReportAdapter extends RecyclerView.Adapter<SalesReportAdapter.MyViewHolder> {

    Context context;
    ArrayList<Sales> salesArrayList;

    public SalesReportAdapter(Context context, ArrayList salesArrayList) {
        this.context = context;
        this.salesArrayList = salesArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_report_item_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Sales sales = salesArrayList.get(position);


        holder.dispTransactionID.setText(sales.getTransactionID());
        holder.dispTransactionAmount.setText(String.valueOf(sales.getAmount()));
        holder.dispPaymentTimestamp.setText(sales.getTimeStamp());
        holder.dispOrderStatus.setText(sales.getPaymentStatus());

        holder.itemView.setOnClickListener(v -> {



        });
    }

    @Override
    public int getItemCount() {
        return salesArrayList == null ? 0 : salesArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView dispTransactionID, dispTransactionAmount, dispPaymentTimestamp, dispOrderStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dispTransactionID = itemView.findViewById(R.id.displayTransactionId);
            dispTransactionAmount = itemView.findViewById(R.id.displayTransactionAmount);
            dispPaymentTimestamp = itemView.findViewById(R.id.displayTransactionTimestamp);
            dispOrderStatus = itemView.findViewById(R.id.displayOrderStatus);


        }
    }


}
