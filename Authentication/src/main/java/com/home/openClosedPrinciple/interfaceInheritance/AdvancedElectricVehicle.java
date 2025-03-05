package com.home.openClosedPrinciple.interfaceInheritance;

public interface AdvancedElectricVehicle extends ElectricVehicle, Autonomous, SelfCharging {
    void upgradeSoftware();
}
