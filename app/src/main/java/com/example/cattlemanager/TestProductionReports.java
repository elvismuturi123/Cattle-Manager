package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.example.cattlemanager.Classses.Cows;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class TestProductionReports extends AppCompatActivity {
    MaterialToolbar materialToolbar;

//    BarChart milkProductionBarChart;
//    BarData barData;
//    BarDataSet barDataSet;
//    ArrayList<BarEntry> cattleBarEntry;
//    String[] cattleCategories;
    int bullCount,cowsCount,heiferCount,weanersCount,calvesCount,steersCount;
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_report);

        materialToolbar=findViewById(R.id.toolBar);
        tableLayout=findViewById(R.id.cattleTable);

//        cattleChart=findViewById(R.id.analysisContainer);
//        cattleReport=findViewById(R.id.cattleCategory);

//        milkProductionBarChart =findViewById(R.id.cattleBarChart);
//        cattleBarEntry=new ArrayList<>();
//        cattleCategories=getApplicationContext().getResources().getStringArray(R.array.cattleCategories);

        fetchData();

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addTableRow(HashMap<String,Integer> data) {
        for(Map.Entry<String, Integer> entry : data.entrySet()){
            TableRow row = new TableRow(this);
            String category = entry.getKey();
            int count = entry.getValue();

            TextView textViewCategory = new TextView(this);
            textViewCategory.setText(category);
            textViewCategory.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            row.addView(textViewCategory);

            TextView textViewCount = new TextView(this);
            textViewCount.setText(String.valueOf(count));
            textViewCount.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            row.addView(textViewCount);
            tableLayout.addView(row);
        }
    }


    // Fetch Milk data from the database
    private void fetchData() {
        DatabaseReference mRef= FirebaseDatabase.getInstance().getReference("cattle_details");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bullCount=cowsCount=calvesCount=heiferCount=weanersCount=steersCount=0;
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Cows cow=dataSnapshot.getValue(Cows.class);
                    if(cow != null){
                        if(cow.getCategory().equals("Bull")){
                            bullCount++;
                        }else if(cow.getCategory().equals("Dairy Cow")){
                            cowsCount++;
                        }else if(cow.getCategory().equals("Calf")){
                            calvesCount++;
                        }else if(cow.getCategory().equals("Heifer")){
                            heiferCount++;
                        } else if (cow.getCategory().equals("Weaner")) {
                            weanersCount++;
                        }else if(cow.getCategory().equals("Steer")){
                            steersCount++;
                        }
                    }
                }
                HashMap<String,Integer> map=new HashMap<>();
                map.put("Bulls",bullCount);
                map.put("Calves",calvesCount);
                map.put("Heifers",heiferCount);
                map.put("Dairy Cows",cowsCount);
                map.put("Steers",steersCount);
                map.put("Weaners",weanersCount);
                addTableRow(map);
//                cattleBarEntry.add(new BarEntry(1,bullCount));
//                cattleBarEntry.add(new BarEntry(2,calvesCount));
//                cattleBarEntry.add(new BarEntry(3,heiferCount));
//                cattleBarEntry.add(new BarEntry(4,cowsCount));
//                cattleBarEntry.add(new BarEntry(5,steersCount));
//                cattleBarEntry.add(new BarEntry(6,weanersCount));
//                drawChart(cattleBarEntry);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void drawChart(ArrayList<BarEntry> cattleBarEntry) {
//
//        barDataSet=new BarDataSet(cattleBarEntry,"Cattle Category");
//        barData=new BarData(barDataSet);
//        milkProductionBarChart.setData(barData);
//
//        barDataSet.setColor(Color.parseColor("#309967"));
//        barDataSet.setValueTextSize(16f);
//        barDataSet.setValueTextColor(Color.BLACK);
//        milkProductionBarChart.getDescription().setEnabled(false);
//        XAxis xAxis= milkProductionBarChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(cattleCategories));
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1);
//        xAxis.setDrawAxisLine(true);
//        xAxis.setGranularityEnabled(true);
//
//
//        milkProductionBarChart.setDragEnabled(true);
//        milkProductionBarChart.setVisibleXRangeMaximum(3);
//
//        Legend legend = milkProductionBarChart.getLegend();
//        //setting the shape of the legend form to line, default square shape
//        legend.setForm(Legend.LegendForm.LINE);
//        //setting the text size of the legend
//        legend.setTextSize(11f);
//        //setting the alignment of legend toward the chart
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        //setting the stacking direction of legend
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        //setting the location of legend outside the chart, default false if not set
//        legend.setDrawInside(false);
//
//        float barSpace=0.05f;
//        float groupSpace =0.75f;
//
//
//        barData.setBarWidth(0.5f);
//        milkProductionBarChart.getXAxis().setAxisMinimum(0);
//        milkProductionBarChart.getXAxis().setAxisMaximum(0+ milkProductionBarChart.getBarData().getGroupWidth(groupSpace,barSpace)*6);
//        milkProductionBarChart.getAxisLeft().setAxisMinimum(0);
//        milkProductionBarChart.setDrawGridBackground(false);
////                    educationBarChart.groupBars(0,groupSpace,barSpace);
//        milkProductionBarChart.invalidate();
//    }
}