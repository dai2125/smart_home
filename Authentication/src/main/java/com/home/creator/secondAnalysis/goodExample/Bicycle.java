package com.home.creator.secondAnalysis.goodExample;

public class Bicycle {
    private Wheel frontWheel;
    private Wheel backWheel;

    public Bicycle(int wheelSize) {
        this.frontWheel = new Wheel(wheelSize);
        this.backWheel = new Wheel(wheelSize);
    }

    public void displayWheelSizes() {
        System.out.println("Front Wheel Size: " + frontWheel.getSize());
        System.out.println("Back Wheel Size: " + backWheel.getSize());
    }
}
