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
        if(true) {
            System.out.println(true);
        }
        if(111 < 333) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }

        throw new UnsupportedOperationException("Fax not supported");
    }

    private boolean aaaaa() {
        return true;
    }

    protected Integer bbbb() {
        return null;
    }
}
