package com.home.creator.BContainsOrCompositelyAggregatesA.badExample;

public class Car {

    public Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void startCar() {
        if (engine != null) {
            engine.start();
        }

        System.out.println("Car is starting.");
    }
}
