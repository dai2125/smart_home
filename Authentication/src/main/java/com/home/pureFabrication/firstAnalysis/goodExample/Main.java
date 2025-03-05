package com.home.pureFabrication.firstAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {
        Order order = new Order(123);
        FileLogger logger = new FileLogger();
        order.process(logger);
    }
}
