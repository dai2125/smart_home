package com.home.openClosedPrinciple.firstAnalysis.goodExample;

public class Bicycle {

    private int gear;
    private int cadence;
    private int speed;

    public Bicycle(int startCadence, int startSpeed, int startGear) {
        this.gear = startGear;
        this.cadence = startCadence;
        this.speed = startSpeed;
    }

    public void printDescription(){
        System.out.println("\nBike is " + "in gear " + this.gear
                + " with a cadence of " + this.cadence +
                " and travelling at a speed of " + this.speed + ". ");
    }
}

