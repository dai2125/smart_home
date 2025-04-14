package com.home.asm;

import lcom.LCOM;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Start {

    String packagePath = "C:\\Users\\Authentication\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
            "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
    String analysePath = "com/home/coupling/firstAnalysis/goodExample";
    String targetClass = "com/home/pureFabrication/fifthExample/PayByCreditCard";

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
//        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
//        String analysePath = "com/home/pureFabrication/fifthExample";

        String packagePath = "C:\\Users\\Lenovo\\IdeaProjects\\Authentication\\Authentication\\src\\main\\java\\com\\home" +
                                "\\singleResponsibilityPrinciple\\firstAnalysis"; //\\goodExample"; //\\badExample";
        String analysePath = "com/home/singleResponsibilityPrinciple/firstAnalysis"; ///goodExample";///badExample";
        String targetClass = "com/home/pureFabrication/fifthExample/PayByCreditCard";

//        List<InspectedClass> inspectedClassList = new ArrayList<>();
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

        System.out.println(inspectedClassList.size());

        for(int i = 0; i < inspectedClassList.size(); i++) {
//            System.out.println(inspectedClassList.get(i).getName() + " " + inspectedClassList.get(i).getFanout());
        }

        System.out.println(print.WELCOME);
        Scanner scanner = new Scanner(System.in);
        String choosenClass = "";

        while(true) {
            System.out.println(print.SYSTEM);
            System.out.println(print.OPTIONS);
            System.out.println(print.GREATERTHAN);
            String input = scanner.nextLine().trim().toLowerCase();

            if(input.equals("exit") || input.equals("quit") || input.equals("q")) {
                System.out.println(print.SHUTDOWN);
                break;
            }

            switch(input.toLowerCase()) {
                case "1": // INDIRECTION PRINCIPLE
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();
                    System.out.println(print.CHOOSEORCHESTRATOR);
                    System.out.println(print.ENTERSTART);
                    System.out.println(print.SYSTEM);

                    String orchestrator = "";
                    orchestrator = scanner.nextLine().trim().toLowerCase();

                    if(orchestrator.equalsIgnoreCase("start")) {
                        for(int i = 0; i < inspectedClassList.size(); i++) {
                            if (inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                                System.out.println(indirectionService.start(inspectedClassList.get(i), inspectedClassList));
                            }
                        }
                    } else {
                        for(int i = 0; i < inspectedClassList.size(); i++) {
                            for(int j = 0; j < inspectedClassList.size(); j++) {
                                if (inspectedClassList.get(i).getName().equalsIgnoreCase(input) && inspectedClassList.get(j).getName().equalsIgnoreCase(orchestrator)) {
                                    indirectionService.setIndirectionBetween(inspectedClassList.get(i), inspectedClassList.get(j));
                                }
                            }
                        }
                    }

                    break;
                case "2": // LOOSE COUPLING
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                            System.out.println(LooseCouplingService.analyzeLooseCouplingOfClass(inspectedClassList.get(i).getFullName(),
                                                numberOfMethodsService.getAllMethodsList(inspectedClassList.get(i).getFullName()),
                                                numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()),
                                                inspectedClassList.get(i).getDit(),
                                                inspectedClassList.get(i).getFanout(),
                                                inspectedClassList.get(i).getIsInterface()));
                        }
                    }
                    break;
                case "3": // INTERFACE SEGREGATION PRINCIPLE
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                            System.out.println(InterfaceSegregationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));
                        }
                    }
                    break;
                case "4": // COHESION
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {

                            System.out.println(CohesionService.analyzeCohesionOfClass(
                                                        inspectedClassList.get(i).getName(),
                                                        inspectedClassList.get(i).getAllInitFields().size(),
                                                        inspectedClassList.get(i).getAllFieldsWithinMethods().size(),
                                                        inspectedClassList.get(i).getYalcom(),
                                                        inspectedClassList.get(i).getLcom4()));
                        }
                    }
                    break;
                case "5": // PROTECTED VARIATION
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {

                            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));

                            System.out.println(ProtectedVariationService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));

//                            System.out.println(CohesionService.analyzeCohesionOfClass(
//                                    inspectedClassList.get(i).getName(),
//                                    inspectedClassList.get(i).getAllInitFields().size(),
//                                    inspectedClassList.get(i).getAllFieldsWithinMethods().size(),
//                                    inspectedClassList.get(i).getYalcom(),
//                                    inspectedClassList.get(i).getLcom4()));
                        }
                    }
                    break;
                case "6": // SINGLE RESPONSIBILTY PRINCIPLE
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {

                            inspectedClassList.get(i).setInterfaceMethodList(numberOfMethodsService.getAllInterfaceMethods(inspectedClassList.get(i).getFullName()));
                            System.out.println(CreatorPrincipleService.singleResponsibilityPrinciple(inspectedClassList.get(i)));
//                            SingleResponsibilityPrincipleService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java");

//                            System.out.println(SingleResponsibilityPrincipleService.start(inspectedClassList.get(i), inspectedClassList.get(i).getFullName(), packagePath + "\\" + inspectedClassList.get(i).getName().replaceAll(".*/", "") + ".java"));

//                            System.out.println(CohesionService.analyzeCohesionOfClass(
//                                    inspectedClassList.get(i).getName(),
//                                    inspectedClassList.get(i).getAllInitFields().size(),
//                                    inspectedClassList.get(i).getAllFieldsWithinMethods().size(),
//                                    inspectedClassList.get(i).getYalcom(),
//                                    inspectedClassList.get(i).getLcom4()));
                        }
                    }
                    break;
                case "11": // CREATOR PRINCIPLE
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
                            System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
//                            creator4(inspectedClassList.get(i).getFullName(), packagePath);
                        }
                    }
                    break;
                case "12": // CREATOR PRINCIPLE

                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
                            System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
//                            System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
//                            creator4(inspectedClassList.get(i).getFullName(), packagePath);
                        }
                    }
                    break;
                case "13": // CREATOR PRINCIPLE
                    printAllClasses();

                    System.out.println(print.CHOOSECLASS);
                    input = scanner.nextLine().trim().toLowerCase();

                    for(int i = 0; i < inspectedClassList.size(); i++) {
                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
//                            System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
//                            System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
                            creator4(inspectedClassList.get(i).getFullName(), packagePath);
                        }
                    }
                    break;
                case "path": // CREATOR PRINCIPLE
                    // C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/com/home/cohesion
//                    printAllClasses();
                    // TODO Pfad einfügen funktioniert probiere ob es auch außerhalb des Projekts funktioniert
                    System.out.println(print.ENTERPATH);
                    input = scanner.nextLine().trim();
//                    System.out.println(input);

                    Path path = Paths.get(input);
                    if(Files.exists(path)) {
//                        System.out.println("Path: " + path);
//                        System.out.println("Path: " + path.getFileSystem());
//                        System.out.println("Path: " + path.getParent());
//                        System.out.println("Path: " + path.getRoot());
//                        System.out.println("Path: " + path.toAbsolutePath());
//                        System.out.println("Path System.getProperty(\"user.dir\"): " + System.getProperty("user.dir"));
                        String replace = System.getProperty("user.dir").replaceAll("\\\\", "/");
//                        System.out.println("Path replace: " + replace);
//                        System.out.println("Path analysePath: " + analysePath);

                        String a = path.toString();
                        a = a.replaceAll("\\\\", "/");
                        a.replaceAll(replace, "");

                        packagePath = path.toString().replaceAll("/", "\\\\");
                        analysePath = a;
                        directory = new File(a);
//                        analysePath = input.replaceFirst(replace, "") + "/";
                        inspectedClassList.clear();
                        printAllClasses();
                        test = packageService.allClasses(directory);

//                        System.out.println("packagePath: " + packagePath);
//                        System.out.println("analysePath: " + analysePath);
//                        System.out.println("directory: " + directory);
                        inspectedClassList = initializeAllClasses(analysePath, packagePath, directory, numberOfChildrenService, depthOfInheritanceTree, fanInService, fanOutService, test, wmcService, numberOfMethodsService, numberOfConstructorsService, numberOfFieldsService);

                    }


                    printAllClasses();
                    break;
                case "print": // CREATOR PRINCIPLE
                    printAllClassesWithMetrics();

//                    System.out.println(print.CHOOSECLASS);
//                    input = scanner.nextLine().trim().toLowerCase();
//
//                    for(int i = 0; i < inspectedClassList.size(); i++) {
//                        if(inspectedClassList.get(i).getName().equalsIgnoreCase(input)) {
////                            System.out.println(creator3(inspectedClassList.get(i).getFullName(), inspectedClassList.get(i).getAmountOfMethods()));
//                            System.out.println(creator1(inspectedClassList.get(i).getName(), inspectedClassList.get(i).getAmountOfMethods()));
////                            creator4(inspectedClassList.get(i).getFullName(), packagePath);
//                        }
//                    }
                    break;
                default:
                    System.out.println(print.UNKNOWNINPUT + input);
                    break;
            }
        }
        scanner.close();

        Iterator<String> iterator = test.iterator();
        while(iterator.hasNext()) {
//            System.out.println(iterator.next());
            String className = iterator.next();

            InspectedClass inspectedClass = new InspectedClass(className.replace(analysePath + "/", ""));
            inspectedClass.setFullName(className);

            numberOfChildrenService = new NumberOfChildrenService(className);
            int a = numberOfChildrenService.calculateNOC(analysePath);
//            System.out.println(className + " has " + a + " children");
            inspectedClass.setNumberOfChildren(a);

            depthOfInheritanceTree = new DepthOfInheritanceTree(className);
            int b = depthOfInheritanceTree.calculateDIT();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b);
            inspectedClass.setDit(b);
            inspectedClass.setIsInterface(depthOfInheritanceTree.checkIfClassIsInterface());

            fanInService = new FanInService(className, packagePath, directory);

            Map<String, Integer> classCallCount = fanInService.analyzePackage(directory, className);

            for(String name : classCallCount.keySet()) {
//                System.out.println(className + " ,call count: " + name + ", " + classCallCount.get(name));
            }

            int c = classCallCount.size();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c);
            inspectedClass.setFanin(c);

            fanOutService = new FanOutService(className, packagePath, directory);

            Iterator<String> itr = test.iterator();
            int d = 0;
            Map<String, Integer> classUseCount;

            while(itr.hasNext()) {
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

            Set<String> iFields = numberOfFieldsService.getAllInitFields(className);
            Set<String> aFields = numberOfFieldsService.getAllFieldsWithinMethods(className);

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

            // TODO PROTECTED VARIATIONS
//            System.out.println(className + " " + ProtectedVariationsService
//                                .analyzeProtectedVariationOfClass(numberOfFieldsService.getAllPrivateFields(className),
//                                                                    allFields.size(),
//                                                                    numberOfMethodsService.getAllMethods(className).size(),
//                                                                    numberOfMethodsService.getAllPrivateMethods(className),
//                                                                    numberOfMethodsService.getAllInterfaceMethods(className).size(),
//                                                                    inspectedClass.getDit(),
//                                                                    inspectedClass.getFanout()));

            // TODO INFORMATION EXPERT
//            InformationExpertService informationExpertService = new InformationExpertService();
//            informationExpertService.analyze(className);

            // TODO LOOSE COUPLING
//            System.out.println(className + " " + LooseCouplingService.analyzeLooseCouplingOfClass(className,
//                                                                                                    numberOfMethodsService.getAllMethodsList(className),
//                                                                                                    numberOfMethodsService.getAllInterfaceMethods(className),
//                                                                                                    inspectedClass.getDit(),
//                                                                                                    inspectedClass.getFanout(),
//                                                                                                    inspectedClass.getIsInterface()));

            // TODO POLYMORPHISM
//            System.out.println();
//            numberOfMethodsService.polymorphism(className);

            // TODO LISKOV SUBSTITUTION PRINCIPLE
//            System.out.println("TODO LISKOV SUBSTITUTION PRINCIPLE");
//            numberOfMethodsService.checkLiskov(className);


            // TODO INTERFACE SEGREGATION PRINCIPLE

//            numberOfMethodsService.checkInstructions(className);

//            System.out.println("Fields:\tMethods:\tConstructors:\tWMC:" +
//                                "\tNOC:\tDIT:\tYLCOM:\tLCOM4v1:\tFANIN:\tFANOUT:\tName:");
//            System.out.println(inspectedClass.getAmountOfFields() + "\t\t" +
//                    inspectedClass.getAmountOfMethods() + "\t\t\t" + inspectedClass.getAmountOfConstructors() + "\t\t\t\t" +
//                    inspectedClass.getWmc() + "\t\t" + inspectedClass.getNumberOfChildren() + "\t\t" +
//                    inspectedClass.getDit() + "\t\t" +
//                    String.format("%.2f", inspectedClass.getYalcom()) + " \t" + inspectedClass.getLcom4v1() + "\t\t\t" +
//                    inspectedClass.getFanin() + "\t\t" + inspectedClass.getFanout() + "\t\t" +
//                    inspectedClass.getName() + "\t\t" +
//                    "LCOM4 = " + inspectedClass.getLcom4() + "\t\t" + "LCOM5 = " + inspectedClass.getLcom5() + "\t\t"
//            );




//            inspectedClass.setMethods(numberOfMethodsService.getAllInterfaceMethods(className));

//            System.out.println("MethodModel: " + inspectedClass.toString());

            ClassReader classReader = new ClassReader(className);
            ClassMethodAnalyzer analyzer = new ClassMethodAnalyzer(allInitFields, allMethods);

            classReader.accept(analyzer, 0);

            Map<String, Set<String>> methodToFields = analyzer.getMethodToFields();

//        cohesionService.getCohesion("com/home/asm/Fields");
//            double lcom4 = CohesionService.calculateLCOM4(methodToFields);
//            System.out.println("lcom4:" + lcom4);
//            double lcom4Designite = CohesionService.calculateLCOM4Designite(methodToFields);
//            inspectedClass.setLcom4v1(lcom4);

//            if(allInitFields.isEmpty()) {
//                lcom4Designite = -1.0;
//            }

//            System.out.println(className.replace(analysePath + "/", "") + ", LCOM: " + lcom4Designite + ", LCOM4: " + lcom4);
//            System.out.println(className.replace(analysePath + "/", "") + " NOF: " + h + ", NOM: " + f
//                                + ", NoConstr: " + g + ", WMC: " + e + ", NoChild: " + a + ", DIT: " + b + ", LCOM: " + lcom4Designite
//                                + ", LCOM4: " + lcom4 + ", FANIN: " + c + ", FANOUT: " + d);


            ClassService.put(inspectedClass);

            String[] args2 = new String[]{"-i", packagePath, "-o", className.replace(analysePath + "/", "")};
//            LCOM.startLcomProcess(args2);

            CreatorPrinciple cp = CreatorPrincipleService.get(className.replace(analysePath + "/", ""));
//            System.out.println(CreatorPrincipleService.printAll());

            // TODO CREATOR PRINCIPLE
            numberOfMethodsService.getListOfNewStatements(className);
//            numberOfFieldsService.checkField(className);
            numberOfFieldsService.visitField(className);
            numberOfFieldsService.visitMethod(className);
//            CreatorPrincipleService.checkAll(className);

//            System.out.println(CreatorPrincipleService.printAll());
//            CreatorPrincipleService.aaa(2);

//            CreatorPrincipleService.case1(className);

            // TODO CREATOR INITIALIZING DATA
//            numberOfMethodsService.checkAll(className);
//            numberOfMethodsService.initializingData2(className);

            Creator4Service.start(className, packagePath + "\\" + className.replaceAll(".*/", "") + ".java");
            // TODO CREATOR INITIALIZING DATA





            // TODO CREATOR PRINCIPLE B RECORDS A
//            System.out.println(CreatorPrincipleService.fourthPrinciple(className, f));
            // TODO CREATOR PRINCIPLE B RECORDS A

            // TODO CREATOR PRINCIPLE B CLOSELY USES A
//            System.out.println(CreatorPrincipleService.firstPrinciple(className, f));
            // TODO CREATOR PRINCIPLE B CLOSELY USES A

//            CreatorPrincipleService.checkFirstPrinciple(className);

//            CreatorPrincipleService.constructorInitialized(className);



            // TODO INTERFACE SEGREGATION PRINCIPLE
//            System.out.println(LiskovSubstitutionPrincipleService.liskovSubstitutionPrinciple(inspectedClass));

            // TODO SINGLE RESPONSIBILITY PRINCIPLE
//            System.out.println(SingleResponsibilityPrincipleService.checkSingleResponsibilityPrinciple(inspectedClass));

//            System.out.println("Fields:\tMethods:\tConstructors:\tWMC:" +
//                    "\tNOC:\tDIT:\tYLCOM:\tLCOM4v1:\tFANIN:\tFANOUT:\tName:");
//            System.out.println(inspectedClass.getAmountOfFields() + "\t\t" +
//                    inspectedClass.getAmountOfMethods() + "\t\t\t" + inspectedClass.getAmountOfConstructors() + "\t\t\t\t" +
//                    inspectedClass.getWmc() + "\t\t" + inspectedClass.getNumberOfChildren() + "\t\t" +
//                    inspectedClass.getDit() + "\t\t" +
//                    String.format("%.2f", inspectedClass.getYalcom()) + " \t" + inspectedClass.getLcom4v1() + "\t\t\t" +
//                    inspectedClass.getFanin() + "\t\t" + inspectedClass.getFanout() + "\t\t" +
//                    inspectedClass.getName() + "\t\t" +
//                    "LCOM4 = " + inspectedClass.getLcom4() + "\t\t" + "LCOM5 = " + inspectedClass.getLcom5() + "\t\t"
//            );

//            System.out.println(CohesionService.analyzeCohesionOfClass(iFields.size(), aFields.size(), inspectedClass.getYalcom(), inspectedClass.getLcom4()));

//            ProtectedVariationsService protectedVariationsService = new ProtectedVariationsService();
//            protectedVariationsService.displayInterfaces(className);

            double tcc = (double) inspectedClass.getNumberOfDirectConnections() / 10;//  / inspectedClass.getNumberOfPossibleConnections();
            double lcc = (double) (inspectedClass.getNumberOfDirectConnections() + inspectedClass.getNumberOfIndirectConnections()) / 10; // / inspectedClass.getNumberOfPossibleConnections();

//            System.out.println("NP = " + inspectedClass.getNumberOfPossibleConnections() +
//                                "\tNDC = " + inspectedClass.getNumberOfDirectConnections() +
//                                "\tNID = " + inspectedClass.getNumberOfIndirectConnections() +
//                                "\tTCC = " + String.format("%.2f",tcc) + //(inspectedClass.getNumberOfDirectConnections() / inspectedClass.getNumberOfPossibleConnections()) +
//                                "\tLCC = " + String.format("%.2f",lcc) //(inspectedClass.getNumberOfDirectConnections() + inspectedClass.getNumberOfIndirectConnections()) / inspectedClass.getNumberOfPossibleConnections()
//                    );


//            System.out.println("Name: " + inspectedClass.getName() + "\n" +
//                                "Fullname: " + inspectedClass.getFullName() + "\n" +
//                                "Fields: " + inspectedClass.getAmountOfFields() + "\n" +
//                                "Methods: " + inspectedClass.getAmountOfMethods() + "\n" +
//                                "Constructors: " + inspectedClass.getAmountOfConstructors() + "\n" +
//                                "WMC: " + inspectedClass.getWmc() + "\n" +
//                                "NOC: " + inspectedClass.getNumberOfChildren() + "\n" +
//                                "DIT: " + inspectedClass.getDit() + "\n" +
//                                "LCOM: " + inspectedClass.getLcom() + "\n" +
//                                "LCOM4: " + inspectedClass.getLcom4() + "\n" +
//                                "FANIN: " + inspectedClass.getFanin() + "\n" +
//                                "FANOUT: " + inspectedClass.getFanout());

        }

//        System.out.println();
//        numberOfConstructorsService = new NumberOfConstructorsService();
//
////        System.out.println(numberOfConstructorsService.countConstructors("com/home/asm/Constructor"));
//
//        numberOfFieldsService = new NumberOfFieldsService();
//        System.out.println("numberOfFieldsService.countPutFields(): " + numberOfFieldsService.countPutFields("com/home/pureFabrication/fifthExample/Demo"));
    }

    private static List<InspectedClass> initializeAllClasses(String analysePath, String packagePath, File directory, NumberOfChildrenService numberOfChildrenService, DepthOfInheritanceTree depthOfInheritanceTree, FanInService fanInService, FanOutService fanOutService, HashSet<String> test, WMCService wmcService, NumberOfMethodsService numberOfMethodsService, NumberOfConstructorsService numberOfConstructorsService, NumberOfFieldsService numberOfFieldsService) throws IOException {
        List<InspectedClass> ans = new ArrayList<>();

        Iterator<String> iterator = test.iterator();
        while(iterator.hasNext()) {
            String className = iterator.next();
//            System.out.println("className = " + className);
//            System.out.println("className = " + className.replaceFirst(".*/", ""));
//            System.out.println("className = " + className.replaceAll(".*/", ""));
            InspectedClass inspectedClass = new InspectedClass(className.replaceAll(".*/", ""));
//            InspectedClass inspectedClass = new InspectedClass(className.replace(analysePath + "/", ""));
            inspectedClass.setFullName(className);

            numberOfChildrenService = new NumberOfChildrenService(className);
            int a = numberOfChildrenService.calculateNOC(packagePath);
//            System.out.println(className + " has " + a + " children");
            inspectedClass.setNumberOfChildren(a);

            depthOfInheritanceTree = new DepthOfInheritanceTree(className);
            int b = depthOfInheritanceTree.calculateDIT();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b);
            inspectedClass.setDit(b);
            inspectedClass.setIsInterface(depthOfInheritanceTree.checkIfClassIsInterface());

            fanInService = new FanInService(className, packagePath, directory);

            Map<String, Integer> classCallCount = fanInService.analyzePackage(directory, className);

            for(String name : classCallCount.keySet()) {
//                System.out.println(className + " ,call count: " + name + ", " + classCallCount.get(name));
            }

            int c = classCallCount.size();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c);
            inspectedClass.setFanin(c);

            fanOutService = new FanOutService(className, packagePath, directory);

            Iterator<String> itr = test.iterator();
            int d = 0;
            Map<String, Integer> classUseCount;

            while(itr.hasNext()) {
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
            String[] args2 = new String[]{"-i", packagePath, "-o", inspectedClass.getName()};
//            String[] args2 = new String[]{"-i", packagePath, "-o", inspectedClass.getFullName().replace(analysePath + "/", "")};
            LCOM.startLcomProcess(args2);



            ans.add(inspectedClass);
//            inspectedClassList.add(inspectedClass);
        }
        return ans;

    }

    private static String creator1(String className, int nom) {
        return CreatorPrincipleService.firstPrinciple(className, nom);
    }

    private static String creator3(String className, int nom) {
        return CreatorPrincipleService.fourthPrinciple(className, nom);
    }

    private static void creator4(String className, String packagePath) {
        Creator4Service.start(className, packagePath + "\\" + className.replaceAll(".*/", "") + ".java");
    }

    private void printAllClasses() {
        for(int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.SYSTEM + inspectedClassList.get(i).getName()); //.replaceFirst(".*/", ""));
        }
    }

    private void printAllClassesWithMetrics() {
        for(int i = 0; i < inspectedClassList.size(); i++) {
            System.out.println(print.SYSTEM + inspectedClassList.get(i).metricToString()); //.replaceFirst(".*/", ""));
        }
    }
}
