package com.home.openClosedPrinciple.firstAnalysis.badExample;

import com.home.openClosedPrinciple.firstAnalysis.badExample.BadExampleBicycle;
import com.home.openClosedPrinciple.firstAnalysis.goodExample.MountainBike;
import com.home.openClosedPrinciple.firstAnalysis.goodExample.RoadBike;

public class BadExampleTestBike {

    public static void main(String[] args) {
        BadExampleBicycle bike01, bike02, bike03;

        bike01 = new BadExampleBicycle(20, 10, 1, "BadExampleMountainBike");
        BadExampleBicycle bike = new BadExampleBicycle(20, 10, 1, "BadExampleRoadBike");
        bike02 = new BadExampleMountainBike(20, 10, 5, "Dual");
        bike03 = new BadExampleRoadBike(40, 20, 8, 23);

        bike01.printDescription();
        bike02.printDescription();
        bike03.printDescription();
    }
}
