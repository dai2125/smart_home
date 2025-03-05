package com.home.singleResponsibilityPrinciple.firstAnalysis;

public class DeliveryApp {

    private Order order;

    public DeliveryApp(Order order) {
        this.order = order;
    }

    public void delivery() {
        System.out.println("Delivering the order");
        System.out.println("Order with order id as " + this.order.getOrderId() + " being delivered to " + this.order.getCustomer().getName());
        System.out.println("Order is to be delivered to: " + this.order.getCustomer().getAddress());
    }
}
