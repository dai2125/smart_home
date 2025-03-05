package com.home.openClosedPrinciple.firstAnalysis.badExample;

public class BadExampleBicycle {

    private int gear;
    private int cadence;
    private int speed;
    private String type; // "BadExampleMountainBike" oder "BadExampleRoadBike"
    private String suspension; // Nur für BadExampleMountainBike
    private int tireWidth; // Nur für BadExampleRoadBike

    public BadExampleBicycle(int startCadence, int startSpeed, int startGear, String type) {
        this.gear = startGear;
        this.cadence = startCadence;
        this.speed = startSpeed;
        this.type = type;
    }

    public void setSuspension(String suspension) {
        if (this.type.equals("BadExampleMountainBike")) {
            this.suspension = suspension;
        }
    }

    public void setTireWidth(int tireWidth) {
        if (this.type.equals("BadExampleRoadBike")) {
            this.tireWidth = tireWidth;
        }
    }

    public void printDescription() {
        System.out.println("\nBike is in gear " + this.gear
                + " with a cadence of " + this.cadence +
                " and travelling at a speed of " + this.speed + ". ");

        if (type.equals("BadExampleMountainBike")) {
            System.out.println("The BadExampleMountainBike has a " + suspension + " suspension.");
        } else if (type.equals("BadExampleRoadBike")) {
            System.out.println("The BadExampleRoadBike has " + tireWidth + " MM tires.");
        }
    }
}
