package com.home.creator.BContainsOrCompositelyAggregatesA.secondAnalysis.aggregation;

public class Car {

    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void startCar() {
        engine.start();
    }
}