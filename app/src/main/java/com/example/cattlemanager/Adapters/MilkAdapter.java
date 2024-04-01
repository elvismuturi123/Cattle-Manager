package com.example.cattlemanager.Adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.EditMilkDetails;
import com.example.cattlemanager.Classses.Milk;
import com.example.cattlemanager.R;
import com.example.cattlemanager.View_all_milk_details;

import java.util.ArrayList;

public class MilkAdapter extends RecyclerView.Adapter<MilkAdapter.MyViewHolder> {
    Context context;
    ArrayList <Milk> retrievedMilkArraylist;
    public MilkAdapter(Context context, ArrayList retrievedMilkArraylist) {
        this.context = context;
        this.retrievedMilkArraylist = retrievedMilkArraylist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_display_item_milk, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Milk milkRecord =retrievedMilkArraylist.get(position);
        holder.milkDate.setText(milkRecord.getMilking_Date());
        holder.cow_Name.setText(milkRecord.getCattleIdentifier());
        holder.milkAllTotal.setText(String.valueOf(milkRecord.getMilk_Total()));
        String milkgDayId = milkRecord.getMilkDay_id();

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMilkDetails.class);
                intent.putExtra("milk", milkRecord);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, View_all_milk_details.class);
            intent.putExtra("milkDay_ID", milkgDayId);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return retrievedMilkArraylist == null ? 0 : retrievedMilkArraylist.size();
    }
    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView milkDate,cow_Name,milkAllTotal;
        AppCompatButton updateBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            milkDate =itemView.findViewById(R.id.disp_milkingDate);
            cow_Name =itemView.findViewById(R.id.disp_Name);
            milkAllTotal =itemView.findViewById(R.id.disp_milkTotal);
            updateBtn= itemView.findViewById(R.id.updateRecordBtn);
        }
    }
}
