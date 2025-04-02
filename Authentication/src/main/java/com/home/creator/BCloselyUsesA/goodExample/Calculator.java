package com.home.creator.BCloselyUsesA.goodExample;

import com.home.creator.BRecordsA.goodExample.Event;

public class Calculator {

    public int esel = 22;
    public int tiger = 48;
    public int wolf = 0;
    Event event7 = new Event(String.valueOf(esel));
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
    }
}
