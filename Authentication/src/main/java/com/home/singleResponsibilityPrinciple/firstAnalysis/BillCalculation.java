package com.home.singleResponsibilityPrinciple.firstAnalysis;

import java.util.Random;

public class BillCalculation {

    private Order order;

    public BillCalculation(Order order) {
        this.order = order;
    }

    public void calculateBill() {
        Random random = new Random();
        int totalAmt = random.nextInt(200) * this.order.getQuantity();

        this.order.setTotalBillAmt(totalAmt);
        System.out.println("Order with order id " + this.order.getOrderId() + " has a total bill amount of " + this.order.getTotalBillAmt());
    }
}
