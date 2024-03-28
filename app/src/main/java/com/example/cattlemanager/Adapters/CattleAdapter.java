package com.example.cattlemanager;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CattleAdapter extends RecyclerView.Adapter<CattleAdapter.MyViewHolder> {

    Context context;
    ArrayList <Cows> retrievedCowsArraylist;
    public CattleAdapter(Context context, ArrayList retrievedCowsArraylist) {
        this.context = context;
        this.retrievedCowsArraylist = retrievedCowsArraylist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_displaycattle, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     Cows cows_data =retrievedCowsArraylist.get(position);
     holder.Cowname.setText(cows_data.getCattle_Name());
     holder.Cowtag.setText(cows_data.getTagNo());
     holder.Cattlebreed.setText(cows_data.getCow_Breed());
     String cow_id = cows_data.getCow_id();



        holder.itemView.setOnClickListener(v -> {

         Intent intent = new Intent(context, ViewAllCattleDetails.class);
         intent.putExtra("COW_ID", cow_id);
         context.startActivity(intent);
     });
    }
    @Override
    public int getItemCount() {
        return retrievedCowsArraylist == null ? 0 : retrievedCowsArraylist.size();
    }
    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView Cowname,Cowtag,Cattlebreed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Cowname =itemView.findViewById(R.id.disp_cowName);
            Cowtag =itemView.findViewById(R.id.disp_cowTagNo);
            Cattlebreed =itemView.findViewById(R.id.disp_cowBreed);

        }
    }


}
