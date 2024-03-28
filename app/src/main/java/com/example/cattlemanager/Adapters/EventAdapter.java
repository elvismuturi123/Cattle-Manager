package com.example.cattlemanager;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    Context context;
    ArrayList <Events> retrievedEventArraylist;
    public EventAdapter(Context context, ArrayList retrievedEventArraylist) {
        this.context = context;
        this.retrievedEventArraylist = retrievedEventArraylist;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_events_item_view, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events events =retrievedEventArraylist.get(position);
        holder.eventDate.setText(events.getEvent_Date());
        holder.eventType.setText(events.getEvent_Type());
        holder.technicianName.setText(events.getTechnician());
        String EventDayId = events.getEvent_id();

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, ViewAllTheEventsActivity.class);
            intent.putExtra("Event_dayId", EventDayId);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return retrievedEventArraylist == null ? 0 : retrievedEventArraylist.size();
    }
    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView eventDate,eventType,technicianName ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventDate =itemView.findViewById(R.id.event_dateid);
            eventType =itemView.findViewById(R.id.event_typeid);
            technicianName =itemView.findViewById(R.id.technician_id);
        }
    }
}
