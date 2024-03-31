package com.example.cattlemanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.Toolbox;
import com.example.cattlemanager.safaricomdaraja.services.DarajaApiClient;


public class Payorder extends AppCompatActivity {

    TextView Uniquecode, Quantity, Totalcost;

    EditText Customerphone;
    Button btn_payOrder;

    String uniqueCode, quantity, orderID;
    double totalCost;

    private DarajaApiClient mApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payorder);


        //ui declaration
        Uniquecode = findViewById(R.id.dispUniqueCode);
        Quantity = findViewById(R.id.dispQuantity);
        Totalcost = findViewById(R.id.dispTotalCost);
        btn_payOrder = findViewById(R.id.btn_payOrder);
        Customerphone = findViewById(R.id.customerPhone);

        // get data from intent

        uniqueCode = getIntent().getStringExtra("uniqueCode");
        quantity = getIntent().getStringExtra("quantity");
        totalCost = getIntent().getDoubleExtra("totalCost", 0.0);
        orderID = getIntent().getStringExtra("orderID");

        //set up the ui

        Uniquecode.setText(uniqueCode);
        Quantity.setText(quantity);
        Totalcost.setText(String.valueOf(totalCost));


        //initalize daraja
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true);

        btn_payOrder.setOnClickListener(v -> {

            String phoneNumber = Customerphone.getText().toString().trim();

            Toolbox.showToast(getApplicationContext(), phoneNumber + " " + totalCost);

        });
    }
}