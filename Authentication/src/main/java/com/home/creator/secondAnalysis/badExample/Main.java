package com.home.creator.secondAnalysis.badExample;

public class Main {
    public static void main(String[] args) {
        WheelFactory factory = new WheelFactory();
        Bicycle bicycle = new Bicycle(factory, 28);
        bicycle.displayWheelSizes();
    }
}
