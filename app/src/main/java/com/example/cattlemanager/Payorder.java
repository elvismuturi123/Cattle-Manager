package com.example.cattlemanager;

import static com.example.cattlemanager.safaricomdaraja.Constants.BUSINESS_SHORT_CODE;
import static com.example.cattlemanager.safaricomdaraja.Constants.CALLBACKURL;
import static com.example.cattlemanager.safaricomdaraja.Constants.PARTYB;
import static com.example.cattlemanager.safaricomdaraja.Constants.PASSKEY;
import static com.example.cattlemanager.safaricomdaraja.Constants.TRANSACTION_TYPE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cattlemanager.Classses.MilkProduct;
import com.example.cattlemanager.Classses.Sales;
import com.example.cattlemanager.Classses.Toolbox;
import com.example.cattlemanager.safaricomdaraja.Utils;
import com.example.cattlemanager.safaricomdaraja.blueprint.AccessToken;
import com.example.cattlemanager.safaricomdaraja.blueprint.STKPush;
import com.example.cattlemanager.safaricomdaraja.services.DarajaApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class Payorder extends AppCompatActivity {
    TextView Uniquecode, Quantity, Totalcost;
    EditText Customerphone;
    Button btn_payOrder;
    String uniqueCode, quantity, orderID, phoneNumber, productReference ,amount;
    double totalCost;
    private DarajaApiClient mApiClient;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference salesRef = firebaseDatabase.getReference("Sales");

    DatabaseReference milkProductRef = firebaseDatabase.getReference("Products");

    DatabaseReference ordersRef = firebaseDatabase.getReference("Orders");
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
        productReference = getIntent().getStringExtra("productReference");

        Toolbox.showToast(getApplicationContext(), productReference);

        //set up the ui

        Uniquecode.setText(uniqueCode);
        Quantity.setText(quantity);
        Totalcost.setText(String.valueOf(totalCost));

        //initalize daraja
        mApiClient = new DarajaApiClient();
        mApiClient.setIsDebug(true);

        btn_payOrder.setOnClickListener(v -> {

            phoneNumber = Customerphone.getText().toString().trim();

            int orderTotalCost = (int) totalCost;
            amount = String.valueOf(orderTotalCost);
            performSTKPush(phoneNumber, amount);
            getAccessToken();
            Toolbox.showToast(getApplicationContext(), phoneNumber + " " + totalCost);

        });
    }
    private void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull retrofit2.Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });

    }

    public void performSTKPush(String phone_number, String amount) {


        String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                Utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                Utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "DeKUTFarm", //Account reference
                "Payment of X"  //Transaction description
        );

        mApiClient.setGetAccessToken(false);

        //Sending the data to the Mpesa API, remember to remove the logging when in production.
        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(Call<STKPush> call, retrofit2.Response<STKPush> response) {

                try {
                    if (response.isSuccessful()) {


                        Timber.d("post submitted to API. %s", response.body());


                        saveSalesDataToFirebase();


                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {

                Timber.e(t);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void saveSalesDataToFirebase() {


        DatabaseReference new_milkSale_record = salesRef.push();
        String autoGeneratedId = new_milkSale_record.getKey();
        String saleID = autoGeneratedId;
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today_date = dateFormat.format(today);

        String transactionID = Toolbox.generateRandomString(10).toUpperCase();

        Sales new_sale_record = new Sales(saleID, orderID, transactionID,
                Toolbox.timestamp2_String(Timestamp.now()), phoneNumber, today_date, "PAID", Double.valueOf(amount));

        salesRef.child(saleID).setValue(new_sale_record, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                if (error != null) {
                    // there is an error
                    Toolbox.showToast(getApplicationContext(), error.getMessage());
                } else {
                    // data was saved
                    Toolbox.showToast(getApplicationContext(), "Payment details captured successfully!");

                    // get available milk
                    milkProductRef.child(productReference).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {

                            String availableMik = "";
                            MilkProduct milkProducts = dataSnapshot.getValue(MilkProduct.class);
                            availableMik = milkProducts.getcTotal();


                            Toolbox.showToast(getApplicationContext(), availableMik);


                            int new_available_milk = Integer.valueOf(availableMik) - Integer.valueOf(quantity);

                            // Check if new_available_milk is negative
                            if (new_available_milk < 0) {
                                new_available_milk = 0; // Reset to zero
                            }

                            Map<String, Object> milk_available_update = new HashMap<>();
                            milk_available_update.put("cTotal", String.valueOf(new_available_milk));

                            milkProductRef.child(productReference).updateChildren(milk_available_update)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toolbox.navigateTo(getApplicationContext(), SalesPageActivity.class);
                                            Toolbox.showToast(getApplicationContext(), "Milk quantity updated successfully.");

                                        }
                                    });
                        }
                    });

                }

            }
        });


    }


}