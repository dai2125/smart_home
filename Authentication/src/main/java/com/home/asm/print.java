package com.home.asm;

public class print {

    private static final String SPACE = " ";
    public static final String INDIRECTION = "Indirection:> ";
    public static final String INDIRECTIONRESULT = "Indirection Result:> ";
    public static final String ISP = "Isp:> ";
    public static final String ISPRESULT = "Isp Result:> ";
    public static final String COUPLING = "Coupling:> ";
    public static final String COUPLINGRESULT = "Coupling Result:> ";
    public static final String COHESION = "Cohesion:> ";
    public static final String COHESIONRESULT = "Cohesion Result:> ";
    public static final String PROTECTEDVARIATIONS = "ProtectedVariations:> ";
    public static final String PROTECTEDVARIATIONSRESULT = "ProtectedVariations Result:> ";
    public static final String SRP = "Srp:> ";
    public static final String SRPRESULT = "Srp Result:> ";
    public static final String CREATOR1 = "Creator 1:> ";
    public static final String CREATOR1RESULT = "Creator 1 Result:> ";
    public static final String CREATOR2 = "Creator 2:> ";
    public static final String CREATOR2RESULT = "Creator 2 Result:> ";
    public static final String CREATOR3 = "Creator 3:> ";
    public static final String CREATOR3RESULT = "Creator 3 Result:> ";
    public static final String CREATOR4 = "Creator 4:> ";
    public static final String CREATOR4RESULT = "Creator 4 Result:> ";
    public static final String METRICS = "Metrics:> ";

    public static final String SYSTEM = "System:> ";
    public static final String GOBACK = "enter back to go back to the main menu ";
    public static final String ENTERCLASS = "enter a class ";
    public static final String INPUTINVALID = "input invalid ";
    public static final String CHOOSECLASS = "choose a class ";
    public static final String SHUTDOWN = SYSTEM + "shut down...";
    public static final String GREATERTHAN = ">";
    public static final String UNKNOWNINPUT = SYSTEM + "unknown input ";
    public static final String ENTERSTART =  INDIRECTION + "or enter start for analyze";
    public static final String CHOOSEORCHESTRATOR = SYSTEM + "choose an orchestrator class";
    public static final String WELCOME = SYSTEM + "Welcome to the GRASP Analyzer";
    public static final String OPTIONS = SYSTEM + "Indirection Principle --> 1\t"
                                        + "Loose Coupling --> 2\t\t\t"
                                        + "Interface Segregation Principle --> 3\n"
                                        + SYSTEM + "Cohesion Principle --> 4\t\t"
                                        + "Protected Variations --> 5\t\t"
                                        + "Single Responsibility Principle --> 6\n"
                                        + SYSTEM + "Creator Principle 1 --> 11\t\t"
                                        + "Creator Principle 2 --> 12\t\t"
                                        + "Creator Principle 4 --> 13\n"
                                        + SYSTEM + "Change Path --> Path\t\t\t"
                                        + "Print Metrics --> Metrics\t\t"
                                        + "Current Directory --> dir\n"
                                        + SYSTEM + "Back --> back\t\t\t\t\t"
                                        + "Exit --> Exit";

                                        ;
    public static final String ENTERPATH = SYSTEM + "enter path for analyze";
    public static final String PATHCHANGED = SYSTEM + "path changed";
    public static final String PATHINVALID = SYSTEM + "path invalid";
    public static final String CONNECTIONSET  = SYSTEM + "connection set between ";
    public static final String AND = "and ";
    public static final String CLASSDOESNTEXIST = SYSTEM + "class does not exist ";

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

    public static String indirectionSetBetween(String owner, String mediator) {
        return INDIRECTION + CONNECTIONSET + owner + AND + mediator;
    }
}
