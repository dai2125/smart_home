package com.home.InterfaceSegregationPrinciple.firstAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {
        Printer basicPrinter = new BasicPrinter();
        basicPrinter.print("Hello World");

        MultiFunctionDevice mfd = new MultiFunctionDevice();
        mfd.print("Report");
        mfd.scan("Document");
    }
}
