package com.home.coupling.firstAnalysis.goodExample;

public class LightBulb implements Switchable {

    @Override
    public void turnOn() {
        System.out.println("Light Bulb is on");
    }
}
