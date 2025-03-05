package com.home.openClosedPrinciple.firstAnalysis.badExample;

public class BadExampleRoadBike extends BadExampleBicycle {
    public BadExampleRoadBike(int startCadence, int startSpeed, int startGear, int tireWidth) {
        super(startCadence, startSpeed, startGear, "BadExampleRoadBike");
        setTireWidth(tireWidth);
    }
}
