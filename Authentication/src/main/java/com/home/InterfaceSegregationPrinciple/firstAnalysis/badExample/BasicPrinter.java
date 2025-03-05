package com.home.InterfaceSegregationPrinciple.firstAnalysis.badExample;

public class BasicPrinter implements Machine {

    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }

    @Override
    public void scan(String document) {
        throw new UnsupportedOperationException("Scan not supported");
    }

    @Override
    public void fax(String document) {
        throw new UnsupportedOperationException("Fax not supported");
    }
}
