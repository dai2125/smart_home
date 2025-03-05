package com.home.creator.BContainsOrCompositelyAggregatesA.secondAnalysis.aggregation;

public class Engine {

    private int horsepower;

    public Engine(int horsepower) {
        this.horsepower = horsepower;
    }

    public void start() {
        System.out.println("Engine starts with " + horsepower + " horsepower.");
    }
}
