package com.home.controllerPrinciple.firstAnalysis.goodExample;

public class Order {
    private int orderId;
    private String status;

    public Order(int orderId) {
        this.orderId = orderId;
        this.status = "Pending";
    }

    public void processOrder() {
        this.status = "Processed";
    }

    public String getStatus() {
        return status;
    }
}
