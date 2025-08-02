package com.home.creator.principle1.goodExample;

import com.home.creator.principle3.goodExample.Event;

public class ReportGenerator {

    private Event event1;
    private Event event4 = new Event("new Event4");
    public String something = "something";
    private Calculator calculator;
    private Calculator calculator2;
    private int num3 = 222;
    private int num4 = 333;

    public ReportGenerator() {
        this.calculator = new Calculator();
        this.event1 = new Event("description");
    }

    public void one(int x, int y) {
        int sum = calculator.add(x, y);
        int difference = calculator.subtract(x, y);
        System.out.println("Report:");
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);

        int num5 = calculator.num1 + calculator.num2;
    }

    int num6 = calculator.num1 + calculator.num2;
    int num7 = calculator.num1 - calculator.num2;

    public void two(int x, int y) {
        int sum = calculator.add(x, y);
        int difference = calculator.subtract(x, y);
        System.out.println("Report:");
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);

        int num8 = calculator.num1 + calculator.num2;
    }

    private static String three() {
        return "Test";
    }

    protected Event four(Event event) {
        event = new Event("new Event");
        Event event1 = new Event("new Event1");
        event.getDescription();
        event1.getDescription();
        event1 = new Event("new Event2");
        event1.getDescription();
        Event event3 = new Event("new Event3");
        event3.getDescription();
        Event event23 = new Event("new event23");

        return event;
    }

    private int five(int a, int b, int c, int d, int e, int f) {
        return a + b + c - (d + e + f);
    }

    public void six(int a, int b) {
        event4.returnTrue();
    }

    int num9 = 200 * 5000;
}
