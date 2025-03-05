package com.home.creator.secondAnalysis.badExample;

public class Bicycle {
    private Wheel frontWheel;
    private Wheel backWheel;

    public Bicycle(WheelFactory factory, int wheelSize) {
        this.frontWheel = factory.createWheel(wheelSize);
        this.backWheel = factory.createWheel(wheelSize);
    }

    public void displayWheelSizes() {
        System.out.println("Front Wheel Size: " + frontWheel.getSize());
        System.out.println("Back Wheel Size: " + backWheel.getSize());
    }
}
