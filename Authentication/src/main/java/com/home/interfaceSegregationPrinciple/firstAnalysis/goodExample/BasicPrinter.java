package com.home.interfaceSegregationPrinciple.firstAnalysis.goodExample;

public class BasicPrinter implements Printer {

    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }
}
