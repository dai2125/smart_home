package com.home.creator.BCloselyUsesA.goodExample;

import com.home.creator.BRecordsA.goodExample.Event;

public class ReportGenerator {

    private Event event;
    public String something = "something";
    private Calculator calculator;
    private Calculator waschmaschine;
    private int esel = 222;
    private int tiger = 333;

    public ReportGenerator() {
        this.calculator = new Calculator();
        this.event = new Event("description");
    }

    public void generateReport(int x, int y) {
        int sum = calculator.add(x, y);
        int difference = calculator.subtract(x, y);
        System.out.println("Report:");
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);

        int AAAAA = calculator.tiger + calculator.esel;
        //calculator.setWolf(calculator.esel - calculator.tiger);
        //calculator.tiger = calculator.wolf;
    }

    int BBBBB = calculator.tiger + calculator.esel;
    int CCCCC = calculator.tiger - calculator.esel;

    public void somethingToTest(int x, int y) {
        int sum = calculator.add(x, y);
        int difference = calculator.subtract(x, y);
        System.out.println("Report:");
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);

        int AAAAA = calculator.tiger + calculator.esel;
        //calculator.setWolf(calculator.esel - calculator.tiger);
        //calculator.tiger = calculator.wolf;
    }

    private static String something() {
        return "TEST";
    }

    protected Event createEvent(Event event) {
        event = new Event("new Event");
        return event;
    }

    private int addition(int a, int b, int c, int d, int e, int f) {
        return a + b + c - (d + e + f);
    }

    public void subtraction(int a, int b) {

    }

    int DDDDD = 200 * 5000;


}
