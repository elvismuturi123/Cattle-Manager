package com.example.cattlemanager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattlemanager.Adapters.CattleAdapter;
import com.example.cattlemanager.Classses.Cows;
import com.example.cattlemanager.Classses.Toolbox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewAllCattleDetails extends AppCompatActivity {
    ArrayList <Cows> cowsArrayList;
    RecyclerView recyclerViewAllCDetails;
    FirebaseDatabase db1 = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    CattleAdapter cattleAdapter;
    TextView Cow_Name;
    TextView TagNumber;
    TextView Breed;
    TextView Weight;
    TextView Birthdate;
    ImageView ShowImage;
    TextView cowNotes;
    TextView cowCategory;

//    AppCompatButton updateCattleRecords;
    String retrieved_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_cattle_details);
         Cow_Name = findViewById(R.id.disp_cowName);
         TagNumber = findViewById(R.id.disp_cowTagNo);
         Breed = findViewById(R.id.disp_cowBreed);
         Weight = findViewById(R.id.disp_cowWeight);
         Birthdate = findViewById(R.id.disp_cowBirthDate);
        ShowImage = findViewById(R.id.disp_cowImage);
        cowNotes = findViewById(R.id.disp_Description);
        cowCategory = findViewById(R.id.disp_cowCategory);
//        updateCattleRecords = findViewById(R.id.navToEditCattle);

        String cow_id = getIntent().getStringExtra("COW_ID");
        DatabaseReference docRef = FirebaseDatabase.getInstance().getReference("cattle_details").child(cow_id);
        Toolbox.showToast(ViewAllCattleDetails.this, "Success!!!!");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {

                        Cows retrieved_cow_data =snapshot.getValue(Cows.class);
                        Cow_Name.setText(retrieved_cow_data.getCattle_Name());
                        TagNumber.setText(retrieved_cow_data.getTagNo());
                        Breed.setText(retrieved_cow_data.getCow_Breed());
                        Weight.setText(retrieved_cow_data.getCattle_Weight());
                        Birthdate.setText(retrieved_cow_data.getDateOfBirth());
                        retrieved_url= retrieved_cow_data.getmImageUrl();
                        cowNotes.setText(retrieved_cow_data.getSnotes());
                        cowCategory.setText(retrieved_cow_data.getCategory());

                        if(retrieved_url.equals(" ")){
//                            //no url
//
//                            // Load an image named "default_image.jpg" from the drawable folder
//                            int imageResource = R.drawable.default_image;
//                            //default image
//                            ShowImage.setImageDrawable(getResources().getDrawable(imageResource));


                            //dont show anything

                        }else {
                            // load the retrieved url
                            loadImage(retrieved_url);
                        }

                        Toolbox.showToast(ViewAllCattleDetails.this, "  Image load Successful!!");
                    } else {
                        // Document does not exist
                        Log.d("TAG", "Document not found");
                    }
                } else {
                    // Handle errors
                    Log.d("TAG", "Error getting document", task.getException());
                }
            }
        });
    }
    public  void  loadImage(String url){

        if(!url.isEmpty()){
            //load image to the imageview
            Picasso.with(getApplicationContext()).load(url).into(ShowImage);

        }else{
            // the is no url
            Toolbox.showToast(getApplicationContext(),"There is no image to show");
        }

    }

}