package com.example.cattlemanager.Adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Classses.Feed;
import com.example.cattlemanager.R;
import com.example.cattlemanager.ViewAllFeedingProgramDetails;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    Context context;
    ArrayList <Feed> retrievedFeedProgramArraylist;
    public FeedAdapter(Context context, ArrayList retrievedFeedProgramArraylist) {
        this.context = context;
        this.retrievedFeedProgramArraylist = retrievedFeedProgramArraylist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedprogram_item_view, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Feed feed =retrievedFeedProgramArraylist.get(position);
        holder.feedDate.setText(feed.getFeedingDtae());
        holder.feedType.setText(feed.getFeedTpe());
        holder.feedMilkTotal.setText(feed.getMilkProduced());
        String feedDayId = feed.getFeedDayId();

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewAllFeedingProgramDetails.class);
            intent.putExtra("feedDayId", feedDayId);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return retrievedFeedProgramArraylist == null ? 0 : retrievedFeedProgramArraylist.size();
    }
    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView feedDate,feedType,feedMilkTotal;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feedDate =itemView.findViewById(R.id.disp_feedDate);
            feedType =itemView.findViewById(R.id.disp_feedType);
            feedMilkTotal =itemView.findViewById(R.id.disp_feedMilkTotal);
        }
    }
}
