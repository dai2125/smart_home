package com.home.creator.BContainsOrCompositelyAggregatesA.badExample;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine(150);
        Car car = new Car(engine);

        car.engine = null;
        car.startCar();
    }
}
