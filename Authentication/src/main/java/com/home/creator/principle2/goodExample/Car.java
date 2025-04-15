package com.home.creator.principle2.goodExample;

public class Car {

    private Engine engine;

    public Car(int engineHorsepower) {
        this.engine = new Engine(engineHorsepower);
    }

    public void startCar() {
        engine.start();
        System.out.println("Car is starting.");
    }
}
