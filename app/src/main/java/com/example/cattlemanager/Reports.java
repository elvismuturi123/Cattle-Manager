package com.example.cattlemanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Adapters.SalesReportAdapter;
import com.example.cattlemanager.Classses.Sales;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reports extends AppCompatActivity {

    Button chooseDate;
    TextView dispChosenDate;
    RecyclerView showSalesReport;
    ArrayList<Sales> salesArrayList;
    SalesReportAdapter salesReportAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Sales");

    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        showSalesReport = findViewById(R.id.salesReportRecyclerView);
        chooseDate = findViewById(R.id.btnChooseDate);
        dispChosenDate = findViewById(R.id.chosenDateDisplay);


        chooseDate.setOnClickListener(v -> {


            Calendar calendar = Calendar.getInstance(); // Get the current date
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(Reports.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int selectedYear,
                                              int selectedMonth, int selectedDay) {

                            year = selectedYear;
                            month = selectedMonth;
                            day = selectedDay; //they are declared here for reuse

                            // Do something with the selected date
                            Calendar selectedDate = Calendar.getInstance();
                            selectedDate.set(year, month, day);


                            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate.getTime());

                            String selectedDateStr = formattedDate;
                            dispChosenDate.setText(selectedDateStr);

                            if (selectedDate.compareTo(Calendar.getInstance()) <= 0) {
                                //load data for the selected date

                                showSalesReport.setVisibility(View.VISIBLE);
                                loadSalesData(selectedDateStr);

                            } else {
                                //hide the recycler view
                                showSalesReport.setVisibility(View.GONE);

                                Toolbox.showToast(getApplicationContext(), "Please select a date in the past or today.");
                            }


                        }
                    }, year, month, day);

            datePickerDialog.show();


        });

        salesArrayList = new ArrayList<>();

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today_date = dateFormat.format(today);


        loadSalesData(today_date);
    }

    private void loadSalesData(String chosenDate) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                salesArrayList.clear(); // clear existing data
                if (snapshot.exists()) {

                    //show the recyclerview

                    showSalesReport.setVisibility(View.VISIBLE);

                    Iterable<DataSnapshot> children = snapshot.getChildren();

                    for (DataSnapshot child : children) {

                        Sales salesRecord = child.getValue(Sales.class);
                        String retrievedDate = salesRecord.getSaleDate();

                        if (chosenDate.equals(retrievedDate)) {

                            salesArrayList.add(salesRecord);
                        }

                        salesReportAdapter = new SalesReportAdapter(getApplicationContext(), salesArrayList);
                        showSalesReport.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        showSalesReport.setAdapter(salesReportAdapter);

                        salesReportAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}