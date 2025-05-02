package com.home.interfaceSegregationPrinciple.firstAnalysis.goodExample;

public class MultiFunctionDevice implements Printer, Scanner {

    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }

    @Override
    public void scan(String document) {
        System.out.println("Scanning: " + document);
    }
}
