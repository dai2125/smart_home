package com.home.asm;

import lcom.LCOM;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

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

    String packagePath = "C:\\Users\\Authentication\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
            "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";

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
        String packagePath = "C:\\Users\\Lenovo\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
                "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
        String analysePath = "com/home/singleResponsibilityPrinciple/firstAnalysis"; ///goodExample";///badExample";

        PackageService packageService = new PackageService();
        File directory = new File(packagePath);
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
                    printAllClasses();
                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    screenChooseOrchestrator();
                    screenIndirection();
//                    System.out.println(print.CHOOSEORCHESTRATOR);
//                    System.out.println(print.ENTERSTART);
//                    System.out.println(print.SYSTEM);

                    String orchestrator = "";
                    orchestrator = scanner.nextLine().trim().toLowerCase();

                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/indirection/firstAnalysis/goodExample

                    if(classEquals(input) && classEquals(orchestrator)) {
                        doIndirectionBetween(input, orchestrator);

                    } else if(classEquals(input) && startEquals(orchestrator)) {
                        doIndirection(input, orchestrator);
                    } else {
                        screenClassDoesntExist(input);
                    }
//                    if (orchestrator.equalsIgnoreCase("start")) {
//                        for (int i = 0; i < inspectedClassList.size(); i++) {
//                            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                                System.out.println(indirectionService.start(inspectedClassList.get(i), inspectedClassList));
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < inspectedClassList.size(); i++) {
//                            for (int j = 0; j < inspectedClassList.size(); j++) {
//                                if (inspectedClassList.get(i).getName().equalsIgnoreCase(input) && inspectedClassList.get(j).getName().equalsIgnoreCase(orchestrator)) {
//                                    indirectionService.setIndirectionBetween(inspectedClassList.get(i), inspectedClassList.get(j));
//                                }
//                            }
//                        }
//                    }

                    break;
                case "2": // LOOSE COUPLING
                    printAllClasses();
                    screenCoupling();

//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

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
//                    }
                    break;
                case "3": // INTERFACE SEGREGATION PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/InterfaceSegregationPrinciple/firstAnalysis/goodExample
                    printAllClasses();

                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
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
                    break;
                case "4": // COHESION
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/cohesion
                    printAllClasses();

                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
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
                    break;
                case "5": // PROTECTED VARIATION
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/protectedVariations/firstAnalysis/goodExample
                    printAllClasses();

                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
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
                    break;
                case "6": // SINGLE RESPONSIBILTY PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/singleResponsibilityPrinciple/firstAnalysis
                    printAllClasses();

                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
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
                    break;
                case "11": // CREATOR PRINCIPLE B CLOSELY USES A
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/principle1/goodExample
                    printAllClasses();

                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
                        doCreator1(input);
                    } else {
                        screenClassDoesntExist(input);
                    }


//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
//                        }
//                    }
                    break;
                case "12": // CREATOR PRINCIPLE B CONTAINS OR COMPOSETILY AGGREGATES A
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/principle3/goodExample
                    printAllClasses();
                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
                        doCreator3(input);
                    } else {
                        screenClassDoesntExist(input);
                    }

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
//                        }
//                    }
                    break;
                case "13": // CREATOR PRINCIPLE B RECORDS A
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/creator/principle3/goodExample

                    printAllClasses();

                    screenChooseClass();
//                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    if(classEquals(input)) {
                        doCreator4(input);
                    } else {
                        screenClassDoesntExist(input);
                    }

//                    for (int i = 0; i < inspectedClassList.size(); i++) {
//                        if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            creator4(inspectedClassList.get(i).getFullName(), packagePath);
//                        }
//                    }
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
                default:
                    screenDefault(input);
//                    System.out.println(print.UNKNOWNINPUT + input);
                    break;
            }
        }
        scanner.close();
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

    private static void creator4(String className, String packagePath) {
        Creator4Service.start(className, packagePath + "\\" + className.replaceAll(".*/", "") + ".java");
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
        System.out.println(print.GREATERTHAN);
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
        System.out.println(print.SYSTEM);
    }

    private void screenIndirectionIsSet(String owner, String mediator) {
        System.out.println(print.indirectionSetBetween(owner, mediator));
    }

    private void screenChooseClass() {
        System.out.println(print.CHOOSECLASS);
    }

    private void screenChooseOrchestrator() {
        System.out.println(print.CHOOSEORCHESTRATOR);
    }

    private void doInidirection() {

    }

    private void screenCoupling() {
        printAllClasses();
        System.out.println(print.CHOOSECLASS);
    }

    private void doCoupling(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        if (classEquals(input)) {
            for (int i = 0; i < inspectedClassList.size(); i++) {
                if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                    System.out.println(LooseCouplingService.analyzeLooseCouplingOfClass(inspectedClassList.get(i).getFullName(),
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
                System.out.println(InterfaceSegregationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
            }
        }
    }

    private void doCohesion(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(CohesionService.analyzeCohesionOfClass(
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
                System.out.println(ProtectedVariationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
            }
        }
    }

    private void doSingleResponsibility(String input) throws IOException {
        NumberOfMethodsService numberOfMethodsService = new NumberOfMethodsService();
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {

                inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                System.out.println(CreatorPrincipleService.singleResponsibilityPrinciple(inspectedClassList.get(i)));
            }
        }
    }

    private void doCreator1(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(creator1(inspectedClassList.get(i)));
//                System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
            }
        }
    }

    private void doCreator3(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                System.out.println(creator3(inspectedClassList.get(i)));
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

    private void doCreator4(String input) {
        for (int i = 0; i < inspectedClassList.size(); i++) {
            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                creator4(inspectedClassList.get(i).getFullName(), packagePath);
            }
        }
    }

    private void screenPathWelcome() {
        System.out.println(print.ENTERPATH);
    }

    private void screenPathChanged() {
        System.out.println(print.PATHCHANGED);
    }

    private void screenPathInvalid() {
        System.out.println(print.PATHINVALID);
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
}
