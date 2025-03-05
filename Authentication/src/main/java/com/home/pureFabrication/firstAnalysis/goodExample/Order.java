package com.home.pureFabrication.firstAnalysis.goodExample;

public class Order {

    private int id;

    public Order(int id) {
        this.id = id;
    }

    public void process(FileLogger logger) {
        logger.log("Processing order with ID: " + id);
    }
}
