package com.home.creator.principle1.badExample;

public class ReportGenerator {

    private Calculator calculator = new Calculator();

    public void generateReport(int x, int y) {
        int sum = calculator.add(x, y);
        int difference = calculator.subtract(x, y);
        System.out.println("Report:");
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
    }
}
