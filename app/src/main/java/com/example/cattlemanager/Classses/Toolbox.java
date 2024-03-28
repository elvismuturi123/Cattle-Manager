package com.example.cattlemanager;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Toolbox {

    public static void navigateTo(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static CollectionReference userCollection() {
        return FirebaseFirestore.getInstance().collection("Users");
    }
}
