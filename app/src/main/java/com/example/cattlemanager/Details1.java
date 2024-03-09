package com.example.cattlemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.cattlemanager.R;

class Details1 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String[] item = {"Fresian", "Jersey", "Guensey"};
        AutoCompleteTextView autoCompleteTextView;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details1);

        ArrayAdapter<String> adapterItems;

        autoCompleteTextView = findViewById(R.id.autoCompleteText);
        adapterItems = new ArrayAdapter<String>(this, R.layout.activity_details1);
        autoCompleteTextView.setAdapter(adapterItems);

    }
}






//autoCompleteTextView.setOnClickListener(new AdapterView.OnItemClickListener(){
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String item = adapterView.getItemAtPosition(position).ToString();
//        Toast.makeText(cattleDetails.this, "Item", Toast.LENGTH_SHORT).show();
//    }
//});


