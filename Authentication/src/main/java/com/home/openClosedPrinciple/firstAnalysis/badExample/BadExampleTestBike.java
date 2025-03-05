package com.home.openClosedPrinciple.firstAnalysis.badExample;

import com.home.openClosedPrinciple.firstAnalysis.goodExample.Bicycle;
import com.home.openClosedPrinciple.firstAnalysis.goodExample.MountainBike;
import com.home.openClosedPrinciple.firstAnalysis.goodExample.RoadBike;

public class BadExampleTestBike {

    public static void main(String[] args) {
        Bicycle bike01, bike02, bike03;

        bike01 = new Bicycle(20, 10, 1);
        Bicycle bike = new Bicycle(20, 10, 1);
        bike02 = new MountainBike(20, 10, 5, "Dual");
        bike03 = new RoadBike(40, 20, 8, 23);

        bike01.printDescription();
        bike02.printDescription();
        bike03.printDescription();
    }
}
