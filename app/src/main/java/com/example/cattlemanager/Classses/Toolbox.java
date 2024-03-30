package com.example.cattlemanager.Classses;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    public static String generateRandomString(int length) {
        String charPool = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charPool.length());
            randomString.append(charPool.charAt(randomIndex));
        }
        return randomString.toString();
    }

    public static String generateRandomNumber(int length) {
        String charPool = "0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charPool.length());
            randomString.append(charPool.charAt(randomIndex));
        }
        return randomString.toString();
    }

    public static String timestamp2_String(Timestamp timestamp) {

        // return new SimpleDateFormat("mm/dd/yyyy").format(timestamp.toDate());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
