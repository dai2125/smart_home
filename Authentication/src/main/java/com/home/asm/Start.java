package com.home.asm;

import lcom.LCOM;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import oshi.util.LsofUtil;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;

public class Start {

    //String packagePath = "C:\\Users\\Authentication\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
    //        "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
    // AuWo Path
    String basePath = System.getProperty("user.dir");
    String packagePath = basePath + "\\Authentication\\src\\main\\java\\com\\home\\cohesion";


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

    private static boolean backEquals(String input) {
        if(input.equalsIgnoreCase("back") || input.equalsIgnoreCase("b")) {
            return true;
        }
        return false;
    }

    public void run() throws IOException {
        //String packagePath = "C:\\Users\\Lenovo\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
        //        "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
        // AuWo Path
        String analysePath = "Authentication\\src\\main\\java\\com\\home\\cohesion"; ///goodExample";///badExample";
        String packagePath = basePath + "\\" + analysePath;
        // System.out.println("1: " + analysePath);
        // System.out.println("2: " + basePath);
        // System.out.println("3: " + packagePath);

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
                        //                    System.out.println(print.CHOOSEORCHESTRATOR);
                        //                    System.out.println(print.ENTERSTART);
                        //                    System.out.println(print.SYSTEM);

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

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(LooseCouplingService.analyzeLooseCouplingOfClass(inspectedClassList.get(i).getFullName(),
//                                    numberOfMethodsService.getAllMethodsList(inspectedClassList.get(i).getFullName()),
//                                    numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()),
//                                    inspectedClassList.get(i).getDit(),
//                                    inspectedClassList.get(i).getFanout(),
//                                    inspectedClassList.get(i).getIsInterface()));
//                        }
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

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
//                            System.out.println(InterfaceSegregationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
//                        }
//                    }
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

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//
//                            System.out.println(CohesionService.analyzeCohesionOfClass(
//                                    inspectedClassList.get(i).getName(),
//                                    inspectedClassList.get(i).getAllInitFields().size(),
//                                    inspectedClassList.get(i).getAllFieldsWithinMethods().size(),
//                                    inspectedClassList.get(i).getYalcom(),
//                                    inspectedClassList.get(i).getLcom4()));
//                        }
//                    }
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

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//
//                            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
//
//                            System.out.println(ProtectedVariationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
//                        }
//                    }
                    }
                    break;
                case "6": // SINGLE RESPONSIBILTY PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/singleResponsibilityPrinciple/firstAnalysis
                    while(true) {

                        printAllClasses(6);

                        screenChooseClass(6);
//                    System.out.println(print.CHOOSECLASS);
                        input = scanner.nextLine().trim().toLowerCase();

                        if(backEquals(input)) {
                            break;
                        } else if(classEquals(input)) {
                            doSingleResponsibility(input);
                        } else {
                            screenClassDoesntExist(input);
                        }

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//
//                            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
//                            System.out.println(CreatorPrincipleService.singleResponsibilityPrinciple(inspectedClassList.get(i)));
//                        }
//                    }
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


//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
//                        }
//                    }
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

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
//                        }
//                    }
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

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            creator4(inspectedClassList.get(i).getFullName(), packagePath);
//                        }
//                    }
                    }
                    break;
                case "path": // CHANGE PATH
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/cohesion
//                    printAllClasses();
                    System.out.println(print.ENTERPATH);
                    input = scanner.nextLine().trim();

                    changePath(input);

//                    Path path = Paths.get(input);
//                    String rootPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
//
//                    if (Files.exists(path) && input.startsWith(rootPath)) {
//
//                        String replace = System.getProperty("user.dir").replaceAll("\\\\", "/");
////                        System.out.println("Path replace: " + replace);
////                        System.out.println("Path analysePath: " + analysePath);
//
//                        String a = path.toString();
//                        a = a.replaceAll("\\\\", "/");
//                        a.replaceAll(replace, "");
//
//                        packagePath = path.toString().replaceAll("/", "\\\\");
//                        analysePath = a;
//                        directory = new File(a);
//
//
////                        analysePath = input.replaceFirst(replace, "") + "/";
//                        inspectedClassList.clear();
//                        printAllClasses();
//                        test = packageService.allClasses(directory);
//
//                        inspectedClassList = initializeAllClasses(analysePath, packagePath, directory, numberOfChildrenService, depthOfInheritanceTree, fanInService, fanOutService, test, wmcService, numberOfMethodsService, numberOfConstructorsService, numberOfFieldsService);
//                        System.out.println(print.PATHCHANGED);
//                    }

                    printAllClasses();
                    break;
                case "print": // PRINT ALL METRICS
                    printAllClassesWithMetrics();
                    break;
                case "test":
                    ClassService.printAllClasses();
                    break;
                case "metrics":
                    printMetrics();
                    break;
                case "dir":
                    printDirectory();
                    break;
                default:
                    screenDefault(input);
//                    System.out.println(print.UNKNOWNINPUT + input);
                    break;
            }
        }
        scanner.close();
    }

    private static void changePathInCategory() {

    }

    private static List<InspectedClass> initializeAllClasses(String analysePath, String packagePath, File directory, NumberOfChildrenService numberOfChildrenService, DepthOfInheritanceTree depthOfInheritanceTree, FanInService fanInService, FanOutService fanOutService, HashSet<String> test, WMCService wmcService, NumberOfMethodsService numberOfMethodsService, NumberOfConstructorsService numberOfConstructorsService, NumberOfFieldsService numberOfFieldsService) throws IOException {
        List<InspectedClass> ans = new ArrayList<>();

        ClassService.clear();

        Iterator<String> iterator = test.iterator();
        while (iterator.hasNext()) {
            String className = iterator.next();
//            System.out.println("initial: analysePath: " + analysePath);
//            System.out.println("initial: packagePath: " + packagePath);
//            System.out.println("initial: directory.getAbsolutePath(): " + directory.getAbsolutePath());


//            System.out.println("className = " + className);
//            System.out.println("className = " + className.replaceFirst(".*/", ""));
//            System.out.println("className = " + className.replaceAll("\\\\", "/"));
            InspectedClass inspectedClass = new InspectedClass(className.replaceAll(".*/", ""));
//            InspectedClass inspectedClass = new InspectedClass(className.replace(analysePath + "/", ""));
            inspectedClass.setFullName(className);

            numberOfChildrenService = new NumberOfChildrenService(className);
            int a = numberOfChildrenService.calculateNOC(inspectedClass.getFullName());
//            System.out.println(className + " has " + a + " children");
            inspectedClass.setNumberOfChildren(a);

            depthOfInheritanceTree = new DepthOfInheritanceTree(className);
            int b = depthOfInheritanceTree.calculateDIT();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b);
            inspectedClass.setDit(b);
            inspectedClass.setIsInterface(depthOfInheritanceTree.checkIfClassIsInterface());

            fanInService = new FanInService(className, packagePath, directory);

            Map<String, Integer> classCallCount = fanInService.analyzePackage(directory, className);

            for (String name : classCallCount.keySet()) {
//                System.out.println(className + " ,call count: " + name + ", " + classCallCount.get(name));
            }

            int c = classCallCount.size();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c);
            inspectedClass.setFanin(c);

            fanOutService = new FanOutService(className, packagePath, directory);

            Iterator<String> itr = test.iterator();
            int d = 0;
            Map<String, Integer> classUseCount;

            while (itr.hasNext()) {
//                classUseCount = fanOutService.analyzePackage(directory, itr.next());
                d = fanOutService.analyzePackage2(className, itr.next());
            }

            inspectedClass.setFanout(d);

            wmcService = new WMCService();
            int e = wmcService.calculateWMC(className);
            inspectedClass.setWmc(e);

            numberOfMethodsService = new NumberOfMethodsService();
            int f = numberOfMethodsService.analyzePackage2(className);
            inspectedClass.setAmountOfMethods(f);

            numberOfConstructorsService = new NumberOfConstructorsService();
            int g = numberOfConstructorsService.countConstructors(className);
            inspectedClass.setAmountOfConstructors(g);

            numberOfFieldsService = new NumberOfFieldsService();
            int h = numberOfFieldsService.countPutFields(className);
            inspectedClass.setAmountOfFields(h);

            inspectedClass.setAllInitFields(numberOfFieldsService.getAllInitFields(className));
            inspectedClass.setAllFieldsWithinMethods(numberOfFieldsService.getAllFieldsWithinMethods(className));
//            Set<String> iFields = numberOfFieldsService.getAllInitFields(className);
//            Set<String> aFields = numberOfFieldsService.getAllFieldsWithinMethods(className);

//            System.out.println("... " + inspectedClass.toString());

//            System.out.println("INIT FIELDS");
//            Iterator<String> iterator2 = iFields.iterator();
//            while(iterator2.hasNext()) {
//                System.out.print(iterator2.next() + " ");
//            }
//
//            System.out.println("\nALL FIELDS");
//            iterator2 = aFields.iterator();
//            while(iterator2.hasNext()) {
//                System.out.print(iterator2.next() + " ");
//            }
//            System.out.println();


            List<String> allMethodsList = numberOfMethodsService.getAllMethodsList(className);
            Set<String> allMethods = numberOfMethodsService.getAllMethods(className);
            Set<String> allFields = numberOfFieldsService.getAllFields(className);
            Set<String> allInitFields = numberOfFieldsService.getAllInitFields(className);

            numberOfMethodsService.getListOfNewStatements(className);
//            numberOfFieldsService.checkField(className);
            numberOfFieldsService.visitField(className);
            numberOfFieldsService.visitMethod(className);

            ClassService.put(inspectedClass);

//            System.out.println("start: " + inspectedClass.getFullName());
//            String[] args2 = new String[]{"-i", packagePath, "-o", inspectedClass.getFullName().replace(analysePath + "/", "")};
//            String[] args2 = new String[]{"-i", packagePath, "-o", inspectedClass.getName()};
//            LCOM.startLcomProcess(args2);


            ans.add(inspectedClass);
//            inspectedClassList.add(inspectedClass);
        }
        String[] args2 = new String[]{"-i", packagePath, "-o", "delete"};
        LCOM.startLcomProcess(args2);

        return ans;

    }

    private static String creator1(InspectedClass inspectedClass) {
        return CreatorPrincipleService.firstPrinciple(inspectedClass);
    }

    private static String creator1(String className, int nom) {
        return CreatorPrincipleService.firstPrinciple(className, nom);
    }

    private static String creator3(InspectedClass inspectedClass) {
        return CreatorPrincipleService.thirdPrinciple(inspectedClass);
    }

    private static String creator3(String className, int nom) {
        return CreatorPrincipleService.thirdPrinciple(className, nom);
    }

    private static String creator4(String className, String packagePath) {
        return Creator4Service.start(className, packagePath + "\\" + className.replaceAll(".*/", "") + ".java");
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
                            inspectedClassList.get(i).getFanout(),
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
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {

                inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                System.out.println(print.SRPRESULT + CreatorPrincipleService.singleResponsibilityPrinciple(inspectedClassList.get(i)));
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
        //System.out.println(print.SYSTEM + " Metrics");
        for(int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.METRICS + inspectedClassList.get(i).metricToString());
        }
    }

    private void printDirectory() {
        System.out.println(print.SYSTEM + packagePath);
    }
}
