package com.home.coupling.firstAnalysis.badExample;

public class Switch {

    private LightBulb lightBulb = new LightBulb();

    public void operate() {
        lightBulb.turnOn();
    }
}
