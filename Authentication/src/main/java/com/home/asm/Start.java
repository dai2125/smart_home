package com.home.asm;

import lcom.LCOM;
import lcom.metrics.TypeMetrics;
import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Start {

    public static void main(String[] args) throws IOException {

//        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
//        String analysePath = "com/home/pureFabrication/fifthExample";
        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home" +
                                "\\polymorphism\\secondAnalysis";
        String analysePath = "com/home/polymorphism/secondAnalysis";
        String targetClass = "com/home/pureFabrication/fifthExample/PayByCreditCard";

        PackageService packageService = new PackageService();
        File directory = new File(packagePath);
        NumberOfChildrenService numberOfChildrenService;
        DepthOfInheritanceTree depthOfInheritanceTree;
        FanInService fanInService;
        FanOutService fanOutService;
        HashSet<String> test = packageService.allClasses(directory);
        WMCService wmcService;
        NumberOfMethodsService numberOfMethodsService;
        NumberOfConstructorsService numberOfConstructorsService;
        NumberOfFieldsService numberOfFieldsService;


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







//            inspectedClass.setMethods(numberOfMethodsService.getAllInterfaceMethods(className));

//            System.out.println("MethodModel: " + inspectedClass.toString());

            ClassReader classReader = new ClassReader(className);
            ClassMethodAnalyzer analyzer = new ClassMethodAnalyzer(allInitFields, allMethods);

            classReader.accept(analyzer, 0);

            Map<String, Set<String>> methodToFields = analyzer.getMethodToFields();

//        cohesionService.getCohesion("com/home/asm/Fields");
            double lcom4 = CohesionService.calculateLCOM4(methodToFields);
            double lcom4Designite = CohesionService.calculateLCOM4Designite(methodToFields);
            inspectedClass.setLcom4v1(lcom4);

            if(allInitFields.isEmpty()) {
                lcom4Designite = -1.0;
            }

//            System.out.println(className.replace(analysePath + "/", "") + ", LCOM: " + lcom4Designite + ", LCOM4: " + lcom4);
//            System.out.println(className.replace(analysePath + "/", "") + " NOF: " + h + ", NOM: " + f
//                                + ", NoConstr: " + g + ", WMC: " + e + ", NoChild: " + a + ", DIT: " + b + ", LCOM: " + lcom4Designite
//                                + ", LCOM4: " + lcom4 + ", FANIN: " + c + ", FANOUT: " + d);


            ClassService.put(inspectedClass);

            String[] args2 = new String[]{"-i", packagePath, "-o", className.replace(analysePath + "/", "")};
            LCOM.startLcomProcess(args2);

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
//
//
        System.out.println();
//        /* TODO */
//        String className = "com/home/pureFabrication/fifthExample/Demo";
//        ClassReader classReader = new ClassReader(className);
//
//
//        numberOfMethodsService = new NumberOfMethodsService();
//        numberOfFieldsService = new NumberOfFieldsService();
//
//        Set<String> allMethods = numberOfMethodsService.getAllMethods(className);
//        Set<String> allFields = numberOfFieldsService.getAllFields(className);
//
//        ClassMethodAnalyzer analyzer = new ClassMethodAnalyzer(allFields, allMethods);
//        classReader.accept(analyzer, 0);
//
//
//        Map<String, Set<String>> methodToFields = analyzer.getMethodToFields();

//        cohesionService.getCohesion("com/home/asm/Fields");
//        double lcom4 = CohesionService.calculateLCOM4(methodToFields);
//        double lcom4Designite = CohesionService.calculateLCOM4Designite(methodToFields);
//
//        System.out.println(className.replace(analysePath + "/", "") + " LCOM4 Designite: " + lcom4Designite + " LCOM4: " + lcom4);
//
//
    }
}
