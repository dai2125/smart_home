package com.home.controllerPrinciple.firstAnalysis.goodExample;

public class Main {
    public static void main(String[] args) {
        Order order = new Order(123);
        OrderController controller = new OrderController(order);

        controller.processOrder();
    }
}
