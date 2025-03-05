package com.home.openClosedPrinciple.interfaceInheritance;

public class TestTesla {

    public static void main(String[] args) {
        TeslaModelS teslaModelS = new TeslaModelS();

        teslaModelS.start();
        teslaModelS.upgradeSoftware();
        teslaModelS.startSelfCharging();
        teslaModelS.chargeBattery();
        teslaModelS.enableAutopilot();
        teslaModelS.stop();
    }
}
