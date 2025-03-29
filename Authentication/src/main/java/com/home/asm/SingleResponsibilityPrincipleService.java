package com.home.asm;

public class SingleResponsibilityPrincipleService {

    // TODO Number of Methods (NoM)
    // TODO Coupling between Objects
    // TODO LCOM
    // TODO Anzahl der direkten Feldzugriffe
    // TODO Methodennamen mit mehreren Verantwortlichkeiten
    // TODO Cyclomatic Complexity (CC)

    public static String checkSingleResponsibilityPrinciple(InspectedClass inspectedClass) {

        if(checkIfClassIsInterface(inspectedClass)) {
            return inspectedClass.getName() + " is an Interface";
        }
        if(!checkYalcom(inspectedClass.getYalcom())) {
            return inspectedClass.getName() + " has an invalid Lcom of " + inspectedClass.getYalcom() + " and doesnt full fill the Single Responsibility Principle";
        }
        if(!checkLcom4(inspectedClass.getLcom4())) {
            return inspectedClass.getName() + " has an invalid Lcom4 of " + inspectedClass.getLcom4() + " and doesnt full fill the Single Responsibility Principle";
        }
        if(!checkFanout(inspectedClass.getFanout())) {
            return inspectedClass.getName() + " has an invalid FANOUT of " + inspectedClass.getFanout() + " and doesnt full fill the Single Responsibility Principle";
        }

        return inspectedClass.getName() + " full fills the Single Responsibility Principle";
    }

    private static boolean checkYalcom(double yalcom) {
        return yalcom <= 0.75;
    }

    private static boolean checkLcom4(double lcom4) {
        return lcom4 <= 2 && lcom4 >= 1;
    }

    private static boolean checkFanout(int fanout) {
        return fanout <= 2;
    }

    private static boolean checkIfClassIsInterface(InspectedClass inspectedClass) {
        return inspectedClass.getIsInterface();
    }
}
