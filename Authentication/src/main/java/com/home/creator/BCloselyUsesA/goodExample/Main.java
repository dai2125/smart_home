package com.home.creator.BCloselyUsesA.goodExample;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        ReportGenerator reportGenerator = new ReportGenerator();
        reportGenerator.generateReport(10, 5);
    }
}
