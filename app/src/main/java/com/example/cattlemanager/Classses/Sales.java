package com.example.cattlemanager.Classses;

public class Sales {
    String saleID,orderID, transactionID, timeStamp,customerNumber,saleDate,paymentStatus;
    Double amount;

    public Sales() {

    }

    public Sales(String saleID, String orderID, String transactionID, String timeStamp, String customerNumber, String saleDate, String paymentStatus, Double amount) {
        this.saleID = saleID;
        this.orderID = orderID;
        this.transactionID = transactionID;
        this.timeStamp = timeStamp;
        this.customerNumber = customerNumber;
        this.saleDate = saleDate;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    public String getSaleID() {
        return saleID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Double getAmount() {
        return amount;
    }
}
