package com.example.cattlemanager;

public class Cows {
    String cow_id;
    String cattle_Name;
    String Category;
    String tagNo;
    String cow_Breed;
    String cattle_Weight;
    String dateOfBirth;
    String snotes;
    String mImageUrl;

    public Cows() {

    }

    public Cows(String cow_id, String cattle_Name, String Category, String tagNo, String cow_Breed, String cattle_Weight, String dateOfBirth, String snotes, String mImageUrl) {
        this.cow_id = cow_id;
        this.cattle_Name = cattle_Name;
        this.Category = Category;
        this.tagNo = tagNo;
        this.cow_Breed = cow_Breed;
        this.cattle_Weight = cattle_Weight;
        this.dateOfBirth = dateOfBirth;
        this.snotes = snotes;
        this.mImageUrl = mImageUrl;
    }

    public String getCow_id() {
        return cow_id;
    }

    public void setCow_id(String cow_id) {
        this.cow_id = cow_id;
    }

    public String getCattle_Name() {
        return cattle_Name;
    }

    public void setCattle_Name(String cattle_Name) {
        this.cattle_Name = cattle_Name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTagNo() {
        return tagNo;
    }

    public void setTagNo(String tagNo) {
        this.tagNo = tagNo;
    }

    public String getCow_Breed() {
        return cow_Breed;
    }

    public void setCow_Breed(String cow_Breed) {
        this.cow_Breed = cow_Breed;
    }

    public String getCattle_Weight() {
        return cattle_Weight;
    }

    public void setCattle_Weight(String cattle_Weight) {
        this.cattle_Weight = cattle_Weight;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSnotes() {
        return snotes;
    }

    public void setSnotes(String snotes) {
        this.snotes = snotes;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String toString(){
        return this.cattle_Name;
        // Assuming that cattle_name is a string field
    }
}




