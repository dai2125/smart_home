package com.home.creator.firstAnalysis.goodExample;

public class Car {

    private Engine engine;

    public Car(String engineType) {
        this.engine = new Engine(engineType);
    }

    public void displayEngineInfo() {
        System.out.println("Car Engine Type: " + engine.getType());
    }
}
