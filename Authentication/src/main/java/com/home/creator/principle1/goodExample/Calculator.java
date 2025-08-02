package com.home.creator.principle1.goodExample;

import com.home.creator.principle3.goodExample.Event;

public class Calculator {

    public int num1 = 22;
    public int num2 = 48;
    public int wolf = 0;
    Event event7 = new Event(String.valueOf(num1));
    boolean test = event7.returnTrue();
    Event event9;
    Event event10;
    Event event11 = new Event("wow");

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        Event event8 = new Event(String.valueOf(wolf));
        event8.getDescription();
        return a - b;

    }

    public void setWolf(int a) {
        wolf = a;
        System.out.println(event7.getDescription());
    }
}
