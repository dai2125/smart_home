package com.home.openClosedPrinciple.firstAnalysis.badExample;

public class BadExampleMountainBike extends BadExampleBicycle {
    public BadExampleMountainBike(int startCadence, int startSpeed, int startGear, String suspensionType) {
        super(startCadence, startSpeed, startGear, "BadExampleMountainBike");
        setSuspension(suspensionType);
    }
}
