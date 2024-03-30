package com.example.cattlemanager.Classses;


public class Order {

    String orderId, orderDate, milkQuantity,orderNo,orderUniqueCode,orderStatus,orderTimestamp;



    private  PaymentData paymentData;


    public Order() {
    }

    public Order(String orderId, String orderDate, String milkQuantity, String orderNo, String orderUniqueCode, String orderStatus, String orderTimestamp, PaymentData paymentData) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.milkQuantity = milkQuantity;
        this.orderNo = orderNo;
        this.orderUniqueCode = orderUniqueCode;
        this.orderStatus = orderStatus;
        this.orderTimestamp = orderTimestamp;
        this.paymentData = paymentData;
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


    public PaymentData getPaymentData() {
        return paymentData;
    }

    public String getOrderTimestamp() {
        return orderTimestamp;
    }

    public  static  class  PaymentData{

        private  double totalAmount;
        private  String paymentTime;
        private  String paymentStatus;
        private  String transactionID;

        public PaymentData() {

        }

        public PaymentData(double totalAmount, String paymentTime, String paymentStatus, String transactionID) {
            this.totalAmount = totalAmount;
            this.paymentTime = paymentTime;
            this.paymentStatus = paymentStatus;
            this.transactionID = transactionID;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public String getPaymentTime() {
            return paymentTime;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public String getTransactionID() {
            return transactionID;
        }
    }

}
