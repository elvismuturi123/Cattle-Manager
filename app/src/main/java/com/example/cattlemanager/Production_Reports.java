package com.example.cattlemanager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.Milk;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Production_Reports extends AppCompatActivity {
    PieChart milkPiechart;

    ArrayList<PieEntry> ictEntry;

    DatabaseReference mRef;
    List<Double> eveningProductionList,morningProductionList,afternoonProductionList;
    HashMap<String,Integer> milkingTimeMap;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_milking_time_report, container, false);

        milkPiechart=view.findViewById(R.id.milkingTimeChart);
        ictEntry=new ArrayList<PieEntry>();
        milkingTimeMap=new HashMap<>();
        eveningProductionList=new ArrayList<Double>();
        morningProductionList=new ArrayList<Double>();
        afternoonProductionList=new ArrayList<Double>();
        mRef= FirebaseDatabase.getInstance().getReference("Milk_Details");

        fetchMilkProduction();
//        computeTotals(morningProductionList,afternoonProductionList,eveningProductionList);
        return view;
    }
    public void computeTotals(List<Double> morningProductionList, List<Double> afternoonProductionList, List<Double> eveningProductionList) {
//        System.out.println(morningProductionList.get(0));
        int morningTotal=0,afternoonTotal = 0,eveningTotal=0;

        for(int n=0;n<morningProductionList.size();n++){
            morningTotal+=morningProductionList.get(n);
        }
        for(int m=0;m<afternoonProductionList.size();m++){
            afternoonTotal+=afternoonProductionList.get(m);
        }
        for(int l=0;l<eveningProductionList.size();l++){
            eveningTotal+=eveningProductionList.get(l);
        }
        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));

        milkingTimeMap.put("Morning",morningTotal);
        milkingTimeMap.put("Afternoon",afternoonTotal);
        milkingTimeMap.put("Evening",eveningTotal);

        for(String milkingTime:milkingTimeMap.keySet()){
            ictEntry.add(new PieEntry(milkingTimeMap.get(milkingTime).floatValue(),milkingTime));
        }
        String label="Milking Time Report";
        PieDataSet pieDataSet=new PieDataSet(ictEntry,label);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setColors(colors);
        PieData pieData=new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        milkPiechart.setData(pieData);
        milkPiechart.setUsePercentValues(true);
        milkPiechart.getDescription().setEnabled(true);
        milkPiechart.setRotationEnabled(true);
        milkPiechart.setDragDecelerationFrictionCoef(0.9f);
        milkPiechart.setRotationAngle(0);
        milkPiechart.setHighlightPerTapEnabled(true);
//        ictPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        milkPiechart.setHoleColor(Color.parseColor("#FFFFFF"));
        milkPiechart.invalidate();
    }

    public void fetchMilkProduction() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Milk milkDetail=dataSnapshot.getValue(Milk.class);
                    double eveningProduction= milkDetail.getEvening_Total();
                    double morningProduction= milkDetail.getMorning_Total();
                    double afternoonProduction= milkDetail.getAfternoon_Total();

                    morningProductionList.add(morningProduction);
                    eveningProductionList.add(eveningProduction);
                    afternoonProductionList.add(afternoonProduction);
                }
                computeTotals(morningProductionList,afternoonProductionList,eveningProductionList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Database Error",error.getMessage());
            }
        });
    }
}