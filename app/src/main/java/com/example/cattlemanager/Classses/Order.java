package com.example.cattlemanager.Classses;


public class Order {

    String orderId, orderDate, milkQuantity,orderNo,orderUniqueCode,orderStatus,orderTimestamp,milkProductReference;

    double orderTotal;


    public Order() {
    }

    public Order(String orderId, String orderDate, String milkQuantity, String orderNo, String orderUniqueCode, String orderStatus, String orderTimestamp, String milkProductReference, double orderTotal) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.milkQuantity = milkQuantity;
        this.orderNo = orderNo;
        this.orderUniqueCode = orderUniqueCode;
        this.orderStatus = orderStatus;
        this.orderTimestamp = orderTimestamp;
        this.milkProductReference = milkProductReference;
        this.orderTotal = orderTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getMilkQuantity() {
        return milkQuantity;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getOrderUniqueCode() {
        return orderUniqueCode;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public String getMilkProductReference() {
        return milkProductReference;
    }

    public double getOrderTotal() {
        return orderTotal;
    }
}
