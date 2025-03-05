package com.home.openClosedPrinciple.interfaceInheritance;

public class TeslaModelS implements AdvancedElectricVehicle {
    @Override
    public void upgradeSoftware() {
        System.out.println("Tesla Model S upgrading software...");
    }

    @Override
    public void enableAutopilot() {
        System.out.println("Tesla Model S enabling autopilot...");
    }

    @Override
    public void chargeBattery() {
        System.out.println("Tesla Model S charging battery...");
    }

    @Override
    public void startSelfCharging() {
        System.out.println("Tesla Model S self charging...");
    }

    @Override
    public void start() {
        System.out.println("Tesla Model S self starting...");
    }

    @Override
    public void stop() {
        System.out.println("Tesla Model S stopping...");
    }
}
