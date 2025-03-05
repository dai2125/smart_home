package com.home.coupling.firstAnalysis.goodExample;

public class Switch {

    private Switchable device;

    public Switch(Switchable device) {
        this.device = device;
    }

    public void operate() {
        device.turnOn();
    }
}
