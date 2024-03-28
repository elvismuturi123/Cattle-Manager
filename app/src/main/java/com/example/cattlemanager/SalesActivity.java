package com.example.cattlemanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.Milk;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SalesActivity extends AppCompatActivity {

    TabLayout tabLayout;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String BreedName;

    DatabaseReference ref = database.getReference("Milk_Details");

    ArrayList<Milk> milkArrayList;

    MilkProductAdapter milkProductAdapter;

    TextView dispBreedName, dispAvailableQuantity, dispPricePerLiter, dispCurrency;

    Button btn_buyMilk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        tabLayout = findViewById(R.id.tabLayout);
        dispBreedName = findViewById(R.id.disp_breedName);
        dispAvailableQuantity = findViewById(R.id.disp_availableQuantity);
        dispCurrency = findViewById(R.id.disp_currency);
        dispPricePerLiter = findViewById(R.id.disp_pricePerLitre);
        btn_buyMilk = findViewById(R.id.btn_buyMilk);


        milkArrayList = new ArrayList<>();


        // Define a Set to store unique cow breeds
        Set<String> uniqueCowBreeds = new HashSet<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through all documents/children
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Access each document/child
                    // Use childSnapshot.getKey() to get the document key (optional)
                    // Use childSnapshot.getValue(<your_data_class>.class) to get the data (cast to your data class)

                    Milk retrieved_milk_record = childSnapshot.getValue(Milk.class);

                    String cowBreed = retrieved_milk_record.getCow_breed();

                    if (!cowBreed.isEmpty() && !uniqueCowBreeds.contains(cowBreed)) {
                        // Add the cow breed to the set of unique cow breeds
                        uniqueCowBreeds.add(cowBreed);

                        // Create and add a new tab only if it's a unique cow breed
                        TabLayout.Tab tab = tabLayout.newTab().setText(cowBreed);
                        tabLayout.addTab(tab);
                    }
                }

                //get the first tab

                TabLayout.Tab firstTab = tabLayout.getTabAt(0);

                if (firstTab != null) {
                    //work with the first tab

                    String firstTabName = firstTab.getText().toString();

                    if (!firstTabName.isEmpty()) {
                        loadMilkData(firstTabName);
                    }

                }

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        int selectedTabPosition = tab.getPosition();

                        BreedName = tab.getText().toString();

                        if (!BreedName.isEmpty()) {
                            loadMilkData(BreedName);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });


    }

    private void loadMilkData(String breedName) {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through all documents/children

                milkArrayList.clear(); // clear the arraylist before adding new milk products


                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Access each document/child
                    // Use childSnapshot.getKey() to get the document key (optional)
                    // Use childSnapshot.getValue(<your_data_class>.class) to get the data (cast to your data class)

                    Milk retrieved_milk_record = childSnapshot.getValue(Milk.class);

                    String cowBreed = retrieved_milk_record.getCow_breed();

                    if (cowBreed.equals(breedName)) {

                        milkArrayList.add(retrieved_milk_record);


                        //setup the user interface


                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });


    }
}