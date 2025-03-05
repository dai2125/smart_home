package com.home.creator.firstAnalysis.badExample;

public class Main {

    public static void main(String[] args) {
        EngineFactory factory = new EngineFactory();
        Engine engine = factory.createEngine("V8");
        Car car = new Car(engine);
        car.displayEngineInfo();
    }
}
