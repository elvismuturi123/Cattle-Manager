package com.example.cattlemanager.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Classses.Order;
import com.example.cattlemanager.Payorder;
import com.example.cattlemanager.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    Context context;
    ArrayList<Order> orderArrayList;

    public OrderAdapter(Context context, ArrayList orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order retreived_order = orderArrayList.get(position);


        holder.Uniquecode.setText(retreived_order.getOrderUniqueCode());
        holder.Quantity.setText(retreived_order.getMilkQuantity());

        holder.itemView.setOnClickListener(v -> {

            Order thisOrder = orderArrayList.get(position);

           double orderTotal = thisOrder.getOrderTotal();

            Intent intent = new Intent(context, Payorder.class);

            intent.putExtra("uniqueCode", thisOrder.getOrderUniqueCode());
            intent.putExtra("quantity", thisOrder.getMilkQuantity());
            intent.putExtra("totalCost", orderTotal);
            intent.putExtra("orderID", thisOrder.getOrderId());
            intent.putExtra("productReference", thisOrder.getMilkProductReference());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderArrayList == null ? 0 : orderArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        TextView Uniquecode, Quantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Uniquecode = itemView.findViewById(R.id.dispUniqueCode);
            Quantity = itemView.findViewById(R.id.dispQuantity);


        }
    }


}
