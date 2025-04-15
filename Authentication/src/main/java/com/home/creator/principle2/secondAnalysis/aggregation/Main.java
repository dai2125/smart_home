package com.home.creator.principle2.secondAnalysis.aggregation;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine(100);
        Car car = new Car(engine);

        car.startCar();
    }
}
