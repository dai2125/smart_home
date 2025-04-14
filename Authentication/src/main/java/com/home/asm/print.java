package com.home.asm;

public class print {

    public static final String SYSTEM = "System:> ";
    public static final String GOBACK = "enter back to go back to the main menu ";
    public static final String ENTERCLASS = "enter a class ";
    public static final String INPUTINVALID = "input invalid ";
    public static final String CHOOSECLASS = "System:> choose a class ";
    public static final String SHUTDOWN = "System:> shut down...";
    public static final String GREATERTHAN = ">";
    public static final String UNKNOWNINPUT = "System:> unknown input";
    public static final String ENTERSTART =  "System:> enter start for analyze";
    public static final String CHOOSEORCHESTRATOR =  "System:> choose a orchestrator class (space seperated)";
    public static final String WELCOME = "System:> Welcome to the GRASP Analyzer";
    public static final String OPTIONS = SYSTEM + "1  --> Indirection Principle\t"
                                        + SYSTEM + "2  --> Loose Coupling\t"
                                        + SYSTEM + "3  --> Interface Segregation Principle\n"
                                        + SYSTEM + "4  --> Cohesion Principle\t"
                                        + SYSTEM + "5  --> Protected Variations\t"
                                        + SYSTEM + "6  --> Single Responsibility Principle\n"
                                        + SYSTEM + "11 --> Creator Principle 1\t"
                                        + SYSTEM + "12 --> Creator Principle 2\t"
                                        + SYSTEM + "13 --> Creator Principle 4\t";
    public static final String ENTERPATH = "System:> enter path for analyze";




    public static String getSystem() {
        return SYSTEM;
    }

    public static String getGoBack() {
        return GOBACK;
    }

    public static String getEnterClass() {
        return ENTERCLASS;
    }

    public static String getInputInvalid() {
        return INPUTINVALID;
    }

    public static String getChooseClass() {
        return CHOOSECLASS;
    }

    public static String getShutDown() {
        return SHUTDOWN;
    }

    public static String getGreaterThan() {
        return GREATERTHAN;
    }

    public static String getUnknownInput() {
        return UNKNOWNINPUT;
    }

}
