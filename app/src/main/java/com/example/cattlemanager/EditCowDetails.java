package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditCowDetails extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;

    private Uri mImageUri;
    StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef2;
    TextView textView;
    // EditText cowBirthDate;
    DatePickerDialog.OnDateSetListener listener;
    EditText cowName;
    EditText tagNumber;
    EditText Cattle_breed;
    EditText Weight;
    EditText dateOfBirth;
    EditText short_notes;
    Button Save_data;
    String cowID = " ";
    DatabaseReference cattleDbRef;
    String Image_Url = " ";
    TextView category;

    Spinner cowCategorySpinner;
    ArrayAdapter CowCategoryArrayAdapter;

    String cowCategory = " ";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cow_details);
        cowName = findViewById(R.id.cname);
        category = findViewById(R.id.cowCategoryTextView);
        tagNumber = findViewById(R.id.ctagnumber);
        Cattle_breed = findViewById(R.id.breed_id);
        Weight = findViewById(R.id.cWeight);
        dateOfBirth = findViewById(R.id.birth_date);
        short_notes = findViewById(R.id.notes_id);
        Save_data = findViewById(R.id.save_button);
        mButtonChooseImage = findViewById(R.id.button_uploadImage);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("cattle_details");

        Cows editCowRecords = getIntent().getParcelableExtra("cows");



            cowID = editCowRecords.getCow_id();
            String cowName1 = editCowRecords.getCattle_Name();
            String cowCategory = editCowRecords.getCategory();
            String TagNumber = editCowRecords.getTagNo();
            String breed = editCowRecords.getCow_Breed();
            String cowWeight = editCowRecords.getCattle_Weight();
            String DateOfBirth = editCowRecords.getDateOfBirth();
            String cowNotesDescription = editCowRecords.getSnotes();




        cowName.setText(cowName1);
        category.setText(cowCategory);
        tagNumber.setText(TagNumber);
        Cattle_breed.setText(breed);
        Weight.setText(cowWeight);
        dateOfBirth.setText(DateOfBirth);
        short_notes.setText(cowNotesDescription);

        Save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cowID = editCowRecords.getCow_id();
                String cowName1 = cowName.getText().toString();
                String cowCategory = category.getText().toString();
                String TagNumber = tagNumber.getText().toString();
                String breed = Cattle_breed.getText().toString();
                String cowWeight = Weight.getText().toString();
                String DateOfBirth = dateOfBirth.getText().toString();
                String cowNotesDescription = short_notes.getText().toString();

                Map<String, Object> updatedMapRecord = new HashMap<>();
                updatedMapRecord.put("cattle_Name", cowName1);
                updatedMapRecord.put("category", cowCategory);
                updatedMapRecord.put("tagNo", TagNumber);
                updatedMapRecord.put("cow_Breed", breed);
                updatedMapRecord.put("cattle_Weight", cowWeight);
                updatedMapRecord.put("dateOfBirth", DateOfBirth);
                updatedMapRecord.put("snotes", cowNotesDescription);



                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child(cowID).updateChildren(updatedMapRecord);
                        Toast.makeText(EditCowDetails.this, "Record Updated", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Display_milk.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditCowDetails.this, "Record Update failed" + error, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }
}