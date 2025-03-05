package com.home.singleResponsibilityPrinciple.secondAnalysis;

import java.util.Random;

public class BillCalculation {

    private OrderDetails orderDetails;

    public BillCalculation(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void calculateBill() {
        Random random = new Random();
        int totalAmount = random.nextInt(200) * this.orderDetails.getQuantity();

        System.out.println("Order with order id " + this.orderDetails.getOrderId() + " has a total bill amount of " + totalAmount);
    }
}
