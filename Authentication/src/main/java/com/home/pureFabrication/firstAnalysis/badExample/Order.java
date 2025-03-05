package com.home.pureFabrication.firstAnalysis.badExample;

public class Order {

    private int id;

    public Order(int id) {
        this.id = id;
    }

    public void process() {
        System.out.println("Processing order with ID: " + id);
    }
}
