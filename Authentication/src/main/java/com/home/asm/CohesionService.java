package com.home.asm;

import java.util.*;

public class CohesionService {

    /* Cohesion
    *
    *
    *
    * */

    /* AuWo Service - Cohesion
    *
    * CohesionService - wie gut ist die Kohäsion der Klasse
    *
    * */

    public static String analyzeCohesionOfClass(String name, int initializedFields, int fieldsUsedWithinMethods, double yalcom, double lcom4) {
        return name + " " + doesThisClassUseAllItsFields(initializedFields, fieldsUsedWithinMethods) + ", " +
                analyzeYalcom(yalcom) + ", " +
                analyzeLcom4(lcom4);
    }

    private static String doesThisClassUseAllItsFields(int initializedFields, int fieldsUsedWithinMethods) {
        if(initializedFields == 0) {
            return "has (" + fieldsUsedWithinMethods + "/" + initializedFields + ") initialized fields";
        } else if(initializedFields > fieldsUsedWithinMethods) {
            return "unused Fields (" + fieldsUsedWithinMethods + "/" + initializedFields + ")";
        } else if(initializedFields == fieldsUsedWithinMethods) {
            return "all Fields are used (" + fieldsUsedWithinMethods + "/" + initializedFields + ")";
        } else {
            return "ERROR initializedFields < fieldsUsedWithinMethods " + initializedFields + " < " + fieldsUsedWithinMethods;
        }
    }

    private static String analyzeYalcom(double yalcom) {
        if(yalcom < 0) {
            return "YALCOM is negative = " + String.format(Locale.US, "%.2f", yalcom);
        } else if(yalcom == 0) {
            return "YALCOM is perfect = " + String.format(Locale.US, "%.2f", yalcom);
        } else if(yalcom < 0.5) {
            return "YALCOM is good = " + String.format(Locale.US, "%.2f", yalcom);
        } else if(yalcom < 0.99) {
            return "YALCOM is very high = " + String.format(Locale.US, "%.2f", yalcom);
        } else {
            return "ERROR";
        }
    }

    private static String analyzeLcom4(double lcom4) {
        if(lcom4 < 0) {
            return "LCOM4 ERROR";
        } else if(lcom4 == 1) {
            return "LCOM4 is perfect = " + lcom4;
        } else if(lcom4 == 2 || lcom4 == 3) {
            return "LCOM4 = " + lcom4 + ", " + String.format(Locale.US, "%.2f", lcom4) + " you may re-design this class";
        } else {
            return "LCOM4 = " + lcom4 + ", " + String.format(Locale.US, "%.2f", lcom4) + " independent components, Cohesion violation";
        }
    }
}
