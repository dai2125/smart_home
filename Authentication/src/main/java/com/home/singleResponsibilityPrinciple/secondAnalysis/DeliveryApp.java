package com.home.singleResponsibilityPrinciple.secondAnalysis;

public class DeliveryApp {

    private OrderManagement orderManagement;

    public DeliveryApp(OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
    }

    public void delivery() {
        System.out.println("Delivering the order");
        System.out.println("Order with order id as " + this.orderManagement.getOrderDetails().getOrderId() + " being delivered to " + this.orderManagement.getCustomer().getName());
        System.out.println("Order is to be delivered to: " + this.orderManagement.getCustomer().getAddress());
    }
}

