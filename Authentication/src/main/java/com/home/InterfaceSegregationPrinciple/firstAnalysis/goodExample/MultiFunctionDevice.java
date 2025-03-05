package com.home.InterfaceSegregationPrinciple.firstAnalysis.goodExample;

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
