package com.home.controllerPrinciple.firstAnalysis.badExample;

public class Main {
    public static void main(String[] args) {
        Order order = new Order(123);

        order.processOrder();
        System.out.println("Order " + order.getStatus());
    }
}
