package com.example.cattlemanager.Classses;

public class MilkProduct {
    String productID;
    String cBreed;
    String cDate;
    String cTotal;
    String cPrice;
    String cNotes;

    public MilkProduct() {

    }

    public MilkProduct(String productID, String cBreed, String cDate, String cTotal, String cPrice, String cNotes) {
        this.productID = productID;
        this.cBreed = cBreed;
        this.cDate = cDate;
        this.cTotal = cTotal;
        this.cPrice = cPrice;
        this.cNotes = cNotes;
    }

    public String getProductID() {
        return productID;
    }

    public String getcBreed() {
        return cBreed;
    }

    public String getcDate() {
        return cDate;
    }

    public String getcTotal() {
        return cTotal;
    }

    public String getcPrice() {
        return cPrice;
    }

    public String getcNotes() {
        return cNotes;
    }
}
