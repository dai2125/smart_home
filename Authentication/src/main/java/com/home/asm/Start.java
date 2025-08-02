package com.home.asm;

import lcom.LCOM;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Start {

    //String packagePath = "C:\\Users\\Authentication\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
    //        "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
    // AuWo Path
    String basePath = System.getProperty("user.dir");
    String packagePath = basePath + "\\Authentication\\src\\main\\java\\com\\home\\cohesion"; // \\secondAnalysis\\badExample";


    List<InspectedClass> inspectedClassList = new ArrayList<>();
    PackageService packageService = new PackageService();
    File directory = new File(packagePath);
    HashSet<String> test = packageService.allClasses(directory);

    public Start() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        Start app = new Start();
        app.run();
    }

    public void run() throws IOException {
        //String packagePath = "C:\\Users\\Lenovo\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
        //        "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
        // AuWo Path
        String analysePath = "Authentication\\src\\main\\java\\com\\home\\cohesion"; //\\secondAnalysis\\badExample"; ///goodExample";///badExample";
        String packagePath = basePath + "\\" + analysePath;
        //System.out.println("1: " + analysePath);
        //System.out.println("2: " + basePath);
        //System.out.println("3: " + packagePath);

        File directory = new File(packagePath);

        PackageService packageService = new PackageService();
        NumberOfChildrenService numberOfChildrenService = null;
        DepthOfInheritanceTree depthOfInheritanceTree = null;
        FanInService fanInService = null;
        FanOutService fanOutService = null;
        HashSet<String> test = packageService.allClasses(directory);
        WMCService wmcService = null;
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        NumberOfConstructorsService numberOfConstructorsService = null;
        NumberOfFieldsService numberOfFieldsService = new NumberOfFieldsService();
//        IndirectionService indirectionService = null;
        IndirectionService indirectionService = new IndirectionService(packagePath, directory);

        inspectedClassList = initializeAllClasses(analysePath, packagePath, directory, numberOfChildrenService, depthOfInheritanceTree, fanInService, fanOutService, test, wmcService, numberOfMethodsService, numberOfConstructorsService, numberOfFieldsService);

        System.out.println(print.WELCOME);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            screenWelcome();

            String input = scanner.nextLine().trim().toLowerCase();

            if (quitEquals(input)) {
                screenShutDown();
                break;
            }

            switch (input.toLowerCase()) {
                case "1": // INDIRECTION PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/indirection/firstAnalysis/goodExample
                    while(true) {
                        printAllClasses(1);
                        screenChooseClass(1);
                        //                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        }

                        screenChooseOrchestrator();
                        screenIndirection();

                        String orchestrator = "";
                        orchestrator = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(orchestrator)) {
                            break;
                        }

                        if(classEquals(input) && classEquals(orchestrator)) {
                            doIndirectionBetween(input, orchestrator);

                        } else if(classEquals(input) && startEquals(orchestrator)) {
                            doIndirection(input, orchestrator);
                        } else {
                            screenClassDoesntExist(input);
                        }
                    }
                    break;
                case "2": // LOOSE COUPLING
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/coupling/fourthAnalysis/goodExample
                    while(true) {
                        // System.out.println("\u001B[31m" + "some test");
                        printAllClasses(2);
                        screenCoupling();

    //                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        }

                        doCoupling(input);

                    }
                    break;
                case "3": // INTERFACE SEGREGATION PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/InterfaceSegregationPrinciple/firstAnalysis/goodExample
                    while(true) {

                        printAllClasses(3);

                        screenChooseClass(3);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;

                        } else if(classEquals(input)) {
                            doInterfaceSegregation(input);
                        } else {
                            screenClassDoesntExist(input);
                        }
                    }
                    break;
                case "4": // COHESION
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/cohesion
                    while(true) {

                        printAllClasses(4);

                        screenChooseClass(4);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            doCohesion(input);
                        } else {
                            screenClassDoesntExist(input);
                        }

                    }
                    break;
                case "5": // PROTECTED VARIATION
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/protectedVariations/firstAnalysis/goodExample
                    while(true) {

                        printAllClasses(5);

                        screenChooseClass(5);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            doProtectedVariation(input);
                        } else {
                            screenClassDoesntExist(input);
                        }

                    }
                    break;
                case "6": // SINGLE RESPONSIBILTY PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/singleResponsibilityPrinciple/firstAnalysis
                    while(true) {

                        printAllClasses(6);

                        screenChooseClass(6);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();
                        System.out.println(input);
                        System.out.println(input + "1");

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            doSingleResponsibility(input);
                        } else if(classEquals(input.replace("1", ""))) {
                            createChart(input.replace("1", ""), 6);
                        } else {
                            screenClassDoesntExist(input);
                        }

                    }
                    break;
                case "11": // CREATOR PRINCIPLE B CLOSELY USES A
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/principle1/goodExample
                    while(true) {

                        printAllClasses(11);

                        screenChooseClass(11);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            doCreator1(input);
                        } else {
                            screenClassDoesntExist(input);
                        }
                    }
                    break;
                case "12": // CREATOR PRINCIPLE B RECORDS A
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/principle3/goodExample
                    while(true) {

                        printAllClasses(12);
                        screenChooseClass(12);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            doCreator3(input);
                        } else {
                            screenClassDoesntExist(input);
                        }
                    }
                    break;
                case "13": // CREATOR PRINCIPLE B CONTAINS OR COMPOSETILY AGGREGATES A
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/principle2/goodExample
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/InitializingData/goodExample
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/InitializingData/thirdExample

                    while (true) {

                        printAllClasses(13);

                        screenChooseClass(13);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            System.out.println(doCreator4(input));
                        } else {
                            screenClassDoesntExist(input);
                        }
                    }
                    break;
                case "path": // CHANGE PATH
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/cohesion
//                    printAllClasses();
                    System.out.println(print.ENTERPATH);
                    input = scanner.nextLine().trim();

                    changePath(input);

                    printAllClasses();
                    break;
                case "print": // PRINT ALL METRICS
                    printAllClassesWithMetrics();
                    break;
                case "test":
                    CreatorPrinciple1And3Service.printAllClasses();
                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        // System.out.println(2222 + " " + inspectedClassList.get(i).getName() + "; " + inspectedClassList.get(i).getYalcom() + ", " + inspectedClassList.get(i).getLcom4());
                    }
                    // ClassService.printAllClasses();
                    break;
                case "metrics":
                    printMetrics();
                    break;
                case "lcom":
                    printCohesionValues();
                case "dir":
                    printDirectory();
                    break;
                case "fields":
                    System.out.println(print.SYSTEM + "choose a class");
                    input = scanner.nextLine().trim().toLowerCase();
                    printFields(input);
                    // System.out.println(printFields(input));
                    break;
                case "methods":
                    System.out.println(print.SYSTEM + "choose a class");
                    input = scanner.nextLine().trim().toLowerCase();
                    printMethods(input);
                    // System.out.println(printMethods(input));
                    break;
                case "classes":
                    System.out.println(print.SYSTEM + "choose a class");
                    input = scanner.nextLine().trim().toLowerCase();

                    System.out.println(printTest(input));
                    break;

                case "aaa":
                    CreatorPrinciple1And3Service.printAllMetrics();
                    break;
                case "bbb":
                    CreatorPrinciple1And3Service.printAllFields();
                    break;
                case "ccc":
                    CreatorPrinciple1And3Service.printAllMethods();
                    break;
                case "ddd":
                    CreatorPrinciple1And3Service.printFirstMethod();
                    break;
                case "e":
                    //doInterfaceSegregationChart();

                    input = "ReportGenerator";
                    createChartCreator(input, 11);


                    //input = "ReportGenerator";
                    //createChart(input, 6);

                    break;
                default:
                    screenDefault(input);
                    break;
            }
        }
        scanner.close();
    }

    private static void changePathInCategory() {

    }

    private static List<InspectedClass> initializeAllClasses(String analysePath, String packagePath, File directory, NumberOfChildrenService numberOfChildrenService, DepthOfInheritanceTree depthOfInheritanceTree, FanInService fanInService, FanOutService fanOutService, HashSet<String> test, WMCService wmcService, NumberOfMethodsService numberOfMethodsService, NumberOfConstructorsService numberOfConstructorsService, NumberOfFieldsService numberOfFieldsService) throws IOException {
        List<InspectedClass> ans = new ArrayList<>();

        CreatorPrinciple1And3Service.clear();
        // ClassService.clear();

        Iterator<String> iterator = test.iterator();
        while (iterator.hasNext()) {
            String className = iterator.next();
//            System.out.println("initial: packagePath: " + packagePath);
//            System.out.println("initial: directory.getAbsolutePath(): " + directory.getAbsolutePath());
//            System.out.println("className = " + className);

            numberOfChildrenService = new NumberOfChildrenService(className);
            int numberOfChildren = numberOfChildrenService.calculateNOC(test);
            //int numberOfChildren = numberOfChildrenService.calculateNOC(analysePath);

            depthOfInheritanceTree = new DepthOfInheritanceTree(className);
            int dit = depthOfInheritanceTree.calculateDIT();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b);

            fanInService = new FanInService(className, packagePath, directory);

            Map<String, Integer> classCallCount = fanInService.analyzePackage(directory, className);

            for (String name : classCallCount.keySet()) {
//                System.out.println(className + " ,call count: " + name + ", " + classCallCount.get(name));
            }

            int fanIn = classCallCount.size();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c);

            fanOutService = new FanOutService(className, packagePath, directory);

            Iterator<String> itr = test.iterator();
            int fanOut = 0;
            Map<String, Integer> classUseCount;

            while (itr.hasNext()) {
//                classUseCount = fanOutService.analyzePackage(directory, itr.next());
                fanOut = fanOutService.analyzePackage2(className, itr.next());
            }

            wmcService = new WMCService();
            int wmc = wmcService.calculateWMC(className);

            numberOfMethodsService = new NumberOfMethodsService();
            int numberOfMethods = numberOfMethodsService.analyzePackage2(className);

            numberOfConstructorsService = new NumberOfConstructorsService();
            int numberOfConstructors = numberOfConstructorsService.countConstructors(className);

            numberOfFieldsService = new NumberOfFieldsService();
            int numberOfFields = numberOfFieldsService.countPutFields(className);

            numberOfMethodsService.getInformationOfMethodsFrom(className);
//            numberOfFieldsService.checkField(className);
            numberOfFieldsService.visitField(className);
            // numberOfFieldsService.visitMethod(className);

//            System.out.println("333: " + className.replaceAll(".*/", "") + ", " + className);
            InspectedClass inspectedClass = new InspectedClass(className.replaceAll(".*/", ""), className,
                    numberOfChildren, dit, depthOfInheritanceTree.checkIfClassIsInterface(),
                    fanIn, fanOut, wmc, numberOfMethods, numberOfConstructors, numberOfFields,
                    numberOfFieldsService.getAllInitFields(className),
                    numberOfFieldsService.getAllFieldsWithinMethods(className));

            if(CreatorPrinciple1And3Service.contains(className.replaceAll(".*/", ""))) {
                InspectedClass inspectedClass2 = CreatorPrinciple1And3Service.get(className.replaceAll(".*/", ""));
//                System.out.println(555 + " " + inspectedClass2.getFullName() + ", " + inspectedClass2.getName() + ", " + inspectedClass2.getLcom4());
                inspectedClass2.setFullName(inspectedClass.getFullName());
                inspectedClass2.setNumberOfConstructors(numberOfConstructors);
                inspectedClass2.setNumberOfChildren(numberOfChildren);
                inspectedClass2.setDit(dit);
                inspectedClass2.setFanIn(fanIn);
                inspectedClass2.setFanOut(fanOut);
                inspectedClass2.setWmc(wmc);
                inspectedClass2.setNumberOfMethods(numberOfMethods);
                inspectedClass2.setNumberOfFields(numberOfFields);
                inspectedClass2.setIsInterface(depthOfInheritanceTree.checkIfClassIsInterface());
                inspectedClass2.setNumberOfConstructors(numberOfConstructors);
                inspectedClass2.setAllInitFields(numberOfFieldsService.getAllInitFields(className));
                inspectedClass2.setAllFieldsWithinMethods(numberOfFieldsService.getAllFieldsWithinMethods(className));
                CreatorPrinciple1And3Service.put(inspectedClass2);
            } else {
                CreatorPrinciple1And3Service.put(inspectedClass);
            }


            //CreatorPrinciple1And3Service.put(inspectedClass);
            // ClassService.put(inspectedClass);

            ans.add(inspectedClass);
        }
        //System.out.println("packagePath: " + packagePath);
        String[] args2 = new String[]{"-i", packagePath, "-o", "delete"};
        LCOM.startLcomProcess(args2);


        return CreatorPrinciple1And3Service.getCreatorPrincipleList();
    }

    private void updateInspectedClassList() {
        CreatorPrinciple1And3Service.getCreatorPrincipleList();
        for(int i = 0; i < CreatorPrinciple1And3Service.getCreatorPrincipleList().size(); i++) {
            for(int j = 0; j < inspectedClassList.size(); j++) {
               //if(CreatorPrinciple1And3Service)
            }
        }
    }

    private static String creator1(InspectedClass inspectedClass) {
        return CreatorPrinciple1And3Service.firstPrinciple(inspectedClass);
    }

    private static String creator1(String className, int nom) {
        return CreatorPrinciple1And3Service.firstPrinciple(className, nom);
    }

    private static String creator3(InspectedClass inspectedClass) {
        return CreatorPrinciple1And3Service.thirdPrinciple(inspectedClass);
    }

    private static String creator3(String className, int nom) {
        return CreatorPrinciple1And3Service.thirdPrinciple(className, nom);
    }

    private static String creator4(String className, String packagePath) {
        return CreatorPrinciple4Service.start(className, packagePath + "\\" + className.replaceAll(".*/", "") + ".java");
    }

    private String terminalCategory(int num) {
        if(num == 1) {
            return print.INDIRECTION;
        } else if(num == 2) {
            return print.COUPLING;
        } else if(num == 3) {
            return print.ISP;
        } else if(num == 4) {
            return print.COHESION;
        } else if(num == 5) {
            return print.PROTECTEDVARIATIONS;
        } else if(num == 6) {
            return print.SRP;
        } else if(num == 11) {
            return print.CREATOR1;
        } else if(num == 12) {
            return print.CREATOR2;
        } else if(num == 13) {
            return print.CREATOR3;
        } else if(num == 14) {
            return print.CREATOR4;
        } else {
            return print.SYSTEM;
        }
    }

    private String principleName(int num) {
        if(num == 1) {
            return print.INDIRECTIONNAME;
        } else if(num == 2) {
            return print.COUPLINGNAME;
        } else if(num == 3) {
            return print.ISPNAME;
        } else if(num == 4) {
            return print.COHESIONNAME;
        } else if(num == 5) {
            return print.PROTECTEDVARIATIONSNAME;
        } else if(num == 6) {
            return print.SRPNAME;
        } else if(num == 11) {
            return print.CREATOR1NAME;
        } else if(num == 12) {
            return print.CREATOR2NAME;
        } else if(num == 13) {
            return print.CREATOR3NAME;
        } else if(num == 14) {
            return print.CREATOR4NAME;
        } else {
            return print.SYSTEM;
        }
    }

    private void printAllClasses(int num) {
        String category = terminalCategory(num);
        for (int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(category + inspectedClassList.get(i).getName()); //.replaceFirst(".*/", ""));
        }
    }

    private void printAllClasses() {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.SYSTEM + inspectedClassList.get(i).getName()); //.replaceFirst(".*/", ""));
        }
    }

    private void printAllClassesWithMetrics() {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.SYSTEM + inspectedClassList.get(i).metricToString()); //.replaceFirst(".*/", ""));
        }
    }

    private void screenWelcome() {
        System.out.println(print.SYSTEM);
        System.out.println(print.OPTIONS);
        System.out.print(print.SYSTEM);
        // System.out.println(print.GREATERTHAN);
    }

    private boolean quitEquals(String input) {
        if (input.equalsIgnoreCase("exit") || input.equals("quit") || input.equals("q")) {
            return true;
        }
        return false;
    }

    private void screenShutDown() {
        System.out.println(print.SHUTDOWN);
    }

    private boolean startEquals(String input) {
        if (input.equalsIgnoreCase("start") || input.equals("s")) {
            return true;
        }
        return false;
    }

    private boolean classEquals(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    private void doIndirection(String className, String input) throws IOException {
        IndirectionService indirectionService = new IndirectionService(packagePath, directory);
        if (startEquals(input)) {
            for (int i = 0; i < inspectedClassList.size(); i++) {
                if (inspectedClassList.get(i).getName().equalsIgnoreCase(className)) {
                    System.out.println(indirectionService.start(inspectedClassList.get(i), inspectedClassList));
                }
            }
        }
    }

    private void doIndirectionBetween(String className, String orchestrator) {
        IndirectionService indirectionService = new IndirectionService(packagePath, directory);
        System.out.println(4 + ". " + packagePath + " . " + directory);
        if (classEquals(className) && classEquals(orchestrator)) {
            for (int i = 0; i < inspectedClassList.size(); i++) {
                for (int j = 0; j < inspectedClassList.size(); j++) {
                    if (inspectedClassList.get(i).getName().equalsIgnoreCase(className) && inspectedClassList.get(j).getName().equalsIgnoreCase(orchestrator)) {
                        indirectionService.setIndirectionBetween(inspectedClassList.get(i), inspectedClassList.get(j));
                        screenIndirectionIsSet(inspectedClassList.get(i).getName(), inspectedClassList.get(j).getName());
                    }
                }
            }
        }
    }

    private void screenIndirection() {
        System.out.println(print.ENTERSTART);
        System.out.print(print.INDIRECTION);
    }

    private void screenIndirectionIsSet(String owner, String mediator) {
        System.out.println(print.indirectionSetBetween(owner, mediator));
    }

    private void screenChooseClass(int num) {
        String category = terminalCategory(num);
        System.out.println(category + print.CHOOSECLASS);
        System.out.print(category);
    }

    private void screenChooseOrchestrator() {
        System.out.println(print.INDIRECTION + print.CHOOSEORCHESTRATOR);
    }

    private void doInidirection() {

    }

    private void screenCoupling() {
        // printAllClasses();
        System.out.println(print.COUPLING + print.CHOOSECLASS);
        System.out.print(print.COUPLING);
    }

    private void doCoupling(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        if (classEquals(input)) {
            for (int i = 0; i < inspectedClassList.size(); i++) {
                if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                    System.out.println(print.COUPLINGRESULT + LooseCouplingService.analyzeLooseCouplingOfClass(inspectedClassList.get(i).getFullName(),
                            numberOfMethodsService.getAllMethodsList(inspectedClassList.get(i).getFullName()),
                            numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()),
                            inspectedClassList.get(i).getDit(),
                            inspectedClassList.get(i).getFanOut(),
                            inspectedClassList.get(i).getIsInterface()));
                }
            }
        } else {
            screenClassDoesntExist(input);
        }
    }

    private void doInterfaceSegregation(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                System.out.println(print.ISPRESULT + InterfaceSegregationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
            }
        }
    }

    private void doInterfaceSegregationChart() throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        List<InspectedClass> inspectedClassList2 = new ArrayList<>();
        for (int i = 0; i < inspectedClassList.size(); i++) {
            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
            inspectedClassList2.add(InterfaceSegregationChartService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
        }

        if(inspectedClassList2 != null) {
            PrincipleChartISP principleChartISP = new PrincipleChartISP("Interface Segregation Principle", inspectedClassList2);
        }

    }

    private void doCohesion(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(print.COHESIONRESULT + CohesionService.analyzeCohesionOfClass(
                        inspectedClassList.get(i).getName(),
                        inspectedClassList.get(i).getAllInitFields().size(),
                        inspectedClassList.get(i).getAllFieldsWithinMethods().size(),
                        inspectedClassList.get(i).getYalcom(),
                        inspectedClassList.get(i).getLcom4()));
            }
        }
    }

    private void doProtectedVariation(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                System.out.println(print.PROTECTEDVARIATIONSRESULT + ProtectedVariationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
            }
        }
    }

    private void doSingleResponsibility(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        for (int i = 0; i < inspectedClassList.size(); i++) {
//            System.out.println("doSingleResponsibility(111): " + inspectedClassList.get(i).getName());
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                System.out.println("doSingleResponsibility(222): " + inspectedClassList.get(i).getName() + " = " + input);
//                System.out.println("doSingleResponsibility(333): " + inspectedClassList.get(i).getFullName() + " = " + input);



                inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                System.out.println(print.SRPRESULT + CreatorPrinciple1And3Service.singleResponsibilityPrinciple(inspectedClassList.get(i)));
            }
        }
    }

    private void doCreator1(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(print.CREATOR1RESULT + creator1(inspectedClassList.get(i)));
//                System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
            }
        }
    }

    private void doCreator3(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(print.CREATOR3RESULT + creator3(inspectedClassList.get(i)));
            }
        }
    }

//    private void doCreator3(String input) {
//        for (int i = 0; i < inspectedClassList.size(); i++) {
//            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
//            }
//        }
//    }

    private String doCreator4(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                return creator4(print.CREATOR4RESULT + inspectedClassList.get(i).getFullName(), packagePath);
            }
        }
        return null;
    }

    private void screenPathWelcome() {
        System.out.println(print.ENTERPATH);
    }

    private void screenPathChanged() {
        System.out.println(print.PATHCHANGED);
    }

    private void screenPathInvalid() {
        System.out.println(print.SYSTEM + print.PATHINVALID);
    }

    private void changePath(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        NumberOfChildrenService numberOfChildrenService = null;
        DepthOfInheritanceTree depthOfInheritanceTree = null;
        FanInService fanInService = null;
        FanOutService fanOutService = null;
        WMCService wmcService = null;
        NumberOfConstructorsService numberOfConstructorsService = null;
        NumberOfFieldsService numberOfFieldsService = new NumberOfFieldsService();
        Path path = Paths.get(input);
        String rootPath = System.getProperty("user.dir").replaceAll("\\\\", "/");

        if (Files.exists(path) && input.startsWith(rootPath)) {

            String replace = System.getProperty("user.dir").replaceAll("\\\\", "/");
//                        System.out.println("Path replace: " + replace);
//                        System.out.println("Path analysePath: " + analysePath);

            String a = path.toString();
            a = a.replaceAll("\\\\", "/");
            a.replaceAll(replace, "");

            packagePath = path.toString().replaceAll("/", "\\\\");
            String analysePath = a;
            directory = new File(a);


//                        analysePath = input.replaceFirst(replace, "") + "/";
            inspectedClassList.clear();
            printAllClasses();
            test = packageService.allClasses(directory);

            inspectedClassList = initializeAllClasses(analysePath, packagePath, directory, numberOfChildrenService, depthOfInheritanceTree, fanInService, fanOutService, test, wmcService, numberOfMethodsService, numberOfConstructorsService, numberOfFieldsService);
            System.out.println(print.PATHCHANGED);
            for(int i = 0; i < inspectedClassList.size(); i++) {
                //System.out.println(444 + inspectedClassList.get(i).getName() + ", "  + inspectedClassList.get(i).getFullName() + ", " + inspectedClassList.get(i).getLcom4());
            }
        } else {
            screenPathInvalid();
        }

//        printAllClasses();
    }

    private void screenDefault(String input) {
        System.out.println(print.UNKNOWNINPUT + input);
    }

    private void screenClassDoesntExist(String input) {
        System.out.println(print.CLASSDOESNTEXIST + input);
    }

    private void printMetrics() {
        for(int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.METRICS + inspectedClassList.get(i).metricToString());
        }
    }

    private void printCohesionValues() {
        for(int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.COHESION + inspectedClassList.get(i).something());
        }
    }

    private void printDirectory() {
        System.out.println(print.SYSTEM + packagePath);
    }

    // case "fields":
    //                    printFields();
    //                    break;
    //                case "methods":
    //                    printMethods();
    //                    break;
    //                case "classes":
    //                    printTest();
    //                    break;

    private String printFields(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                //System.out.println(11 + inspectedClassList.get(i).fieldInformation());
                return inspectedClassList.get(i).fieldInformation();
            }
        }

        /*
        for(int i = 0; i < CreatorPrinciple1And3Service.getCreatorPrincipleList().size(); i++) {
            if(CreatorPrinciple1And3Service.getCreatorPrincipleList().get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(22 + CreatorPrinciple1And3Service.getCreatorPrincipleList().get(i).fieldInformation());
            }
        }
        */
        return null;
    }

    private String printMethods(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                //System.out.println(11 + inspectedClassList.get(i).methodInformation());
                return inspectedClassList.get(i).methodInformation();
            }
        }

        //for(int i = 0; i < CreatorPrinciple1And3Service.getCreatorPrincipleList().size(); i++) {
        //    if(CreatorPrinciple1And3Service.getCreatorPrincipleList().get(i).getName().equalsIgnoreCase(input)) {
        //        System.out.println(22 + CreatorPrinciple1And3Service.getCreatorPrincipleList().get(i).methodInformation());
        //    }
        //}
        return null;
    }

    private String printTest(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                return inspectedClassList.get(i).creatorPrincpleToString();
            }
        }
        return null;
    }

    private static boolean backEquals(String input) {
        if(input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
            return true;
        }
        return false;
    }
    
    private void createChart(String input, int num) {
        // 6
        String principleName = principleName(num);
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                // System.out.println(222222222);
                InspectedClass inspectedClass = inspectedClassList.get(i);
                PrinicpleChart chart = new PrinicpleChart(principleName, inspectedClass);

            }
        }
    } // createChartCreator

    private void createChartCreator(String input, int num) {
        // 11
        String principleName = principleName(num);
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                // System.out.println(222222222);
                InspectedClass inspectedClass = inspectedClassList.get(i);
                PrincipleChartCreator chart = new PrincipleChartCreator();

            }
        }
    }
}
