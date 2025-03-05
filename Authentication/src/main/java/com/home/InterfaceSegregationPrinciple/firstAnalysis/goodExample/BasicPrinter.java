package com.home.InterfaceSegregationPrinciple.firstAnalysis.goodExample;

public class BasicPrinter implements Printer {

    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }
}
