package com.home.asm;

import java.util.List;

public class LooseCouplingService {

    // TODO DIT == 1 damit verlangst du das nur Klassen das Prinzip erfüllen die von einer anderen Klassen Methoden implementieren
    public static String analyzeLooseCouplingOfClass(String className, List<String> allMethods, List<String> allInterfaceMethods, int dit, int fanout, boolean isInterface) {
        if(isInterface) {
            return className + " is an Interface, no loose coupling analysis";
        }
        return areAllMethodsFromAnInterface(allMethods.size(), allInterfaceMethods.size()) + " "
                + amountOfFanout(fanout) + " "
                + analyzeDit(dit) + "\n"
                + looseCouplingCheck(className, allMethods.size(), allInterfaceMethods.size(), dit, fanout);
    }

    private static String areAllMethodsFromAnInterface(int methods, int interfaceMethods) {
        if(methods == 0) {
            return "ERROR class has no methods";
        } else if(methods == interfaceMethods) {
            return "all methods are from an interface";
        } else {
            return "of " + methods + " methods " + interfaceMethods + " are from an interface";
        }
    }

    private static String amountOfFanout(int fanout) {
        if(fanout == 0) {
            return "class has a fanout of " + fanout + " which means less coupling";
        } else if(fanout == 1) {
            return "class has a fanout of " + fanout + " which mean class has a coupling";
        } else if(fanout >= 2) {
            return "class has a fanout of " + fanout +" which means class has a strong coupling";
        } else {
            return "ERROR negaitve fanout value";
        }
    }

    private static String analyzeDit(int dit) {
        if(dit == 1) {
            return "the dit is " + dit + " should be higher";
        } else if(dit > 1) {
            return "the dit is " + dit + " which is good";
        } else {
            return "ERROR negative dit";
        }
    }

    private static boolean methodCheck(int methods, int interfaceMethods) {
        if(methods == 0) {
            return false;
        } else if(methods == interfaceMethods) {
            return true;
        } else if(methods + 2 >= interfaceMethods) {
            return true;
        }
        return false;
    }

    private static boolean fanoutCheck(int fanout) {
        if(fanout == 0) {
            return true;
        } else if(fanout == 1) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean ditCheck(int dit) {
        if(dit == 1) {
            return false;
        } else if(dit > 1) {
            return true;
        } else {
            return false;
        }
    }

    private static String looseCouplingCheck(String className, int methods, int interfaceMethods, int dit, int fanout) {
        if(!methodCheck(methods, interfaceMethods)) {
            return className + " has no methods";
        } else if(!fanoutCheck(fanout) || !ditCheck(dit)) {
            return className + " hasnt a good coupling behavior";
        } else if(methodCheck(methods, interfaceMethods) && fanoutCheck(fanout) && ditCheck(dit)) {
            return className + " fullfills the loose coupling principle";
        }
        return "ERROR in looseCouplingCheck(): ";
    }

}
