package com.home.controllerPrinciple.firstAnalysis.goodExample;

public class OrderController {
    private Order order;

    public OrderController(Order order) {
        this.order = order;
    }

    public void processOrder() {
         order.processOrder();
        System.out.println("Order " + order.getStatus());
    }
}
