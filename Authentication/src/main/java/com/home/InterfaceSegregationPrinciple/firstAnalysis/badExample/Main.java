package com.home.InterfaceSegregationPrinciple.firstAnalysis.badExample;

public class Main {

    public static void main(String[] args) {
        Machine basicPrinter = new BasicPrinter();
        basicPrinter.print("Hello World");

         basicPrinter.scan("Document");
         basicPrinter.fax("Document");
    }
}
