package com.example.cattlemanager;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Milk implements Parcelable {
    String cattleIdentifier;
    String cow_breed;
    String milkDay_id;
    String milking_Date;
    double morning_Total;
    double afternoon_Total;
    double evening_Total;
    double milk_Total;
    String milk_Notes;

    public Milk() {
    }

    public Milk(String cattleIdentifier, String cow_breed, String milkDay_id, String milking_Date, double morning_Total, double afternoon_Total, double evening_Total, double milk_Total, String milk_Notes) {
        this.cattleIdentifier = cattleIdentifier;
        this.cow_breed = cow_breed;
        this.milkDay_id = milkDay_id;
        this.milking_Date = milking_Date;
        this.morning_Total = morning_Total;
        this.afternoon_Total = afternoon_Total;
        this.evening_Total = evening_Total;
        this.milk_Total = milk_Total;
        this.milk_Notes = milk_Notes;
    }

    protected Milk(Parcel in) {
        cattleIdentifier = in.readString();
        milkDay_id = in.readString();
        milking_Date = in.readString();
        morning_Total = in.readDouble();
        afternoon_Total = in.readDouble();
        evening_Total = in.readDouble();
        milk_Total = in.readDouble();
        milk_Notes = in.readString();
    }

    public static final Creator<Milk> CREATOR = new Creator<Milk>() {
        @Override
        public Milk createFromParcel(Parcel in) {
            return new Milk(in);
        }

        @Override
        public Milk[] newArray(int size) {
            return new Milk[size];
        }
    };

    public String getCattleIdentifier() {
        return cattleIdentifier;
    }

    public String getCow_breed() {
        return cow_breed;
    }

    public String getMilkDay_id() {
        return milkDay_id;
    }

    public String getMilking_Date() {
        return milking_Date;
    }

    public double getMorning_Total() {
        return morning_Total;
    }

    public double getAfternoon_Total() {
        return afternoon_Total;
    }

    public double getEvening_Total() {
        return evening_Total;
    }

    public double getMilk_Total() {
        return milk_Total;
    }

    public String getMilk_Notes() {
        return milk_Notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(cattleIdentifier);
        dest.writeString(milkDay_id);
        dest.writeString(milking_Date);
        dest.writeDouble(morning_Total);
        dest.writeDouble(afternoon_Total);
        dest.writeDouble(evening_Total);
        dest.writeDouble(milk_Total);
        dest.writeString(milk_Notes);
    }
}
