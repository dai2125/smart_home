package com.home.creator.firstAnalysis.badExample;

public class Car {

    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void displayEngineInfo() {
        System.out.println("Car Engine Type: " + engine.getType());
    }
}
