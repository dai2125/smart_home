package com.home.asm;

import org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Start {

    public static void main(String[] args) throws IOException {

//        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
//        String analysePath = "com/home/pureFabrication/fifthExample";
        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home" +
                                "\\pureFabrication\\fifthExample";
        String analysePath = "com/home/pureFabrication/fifthExample";
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
            numberOfChildrenService = new NumberOfChildrenService(className);
            int a = numberOfChildrenService.calculateNOC(analysePath);
//            System.out.println(className + " has " + a + " children");

            depthOfInheritanceTree = new DepthOfInheritanceTree(className);
            int b = depthOfInheritanceTree.calculateDIT();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b);

            fanInService = new FanInService(className, packagePath, directory);

            Map<String, Integer> classCallCount = fanInService.analyzePackage(directory, className);

            for(String name : classCallCount.keySet()) {
//                System.out.println(className + " ,call count: " + name + ", " + classCallCount.get(name));
            }

            int c = classCallCount.size();
//            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c);

            fanOutService = new FanOutService(className, packagePath, directory);

            Iterator<String> itr = test.iterator();
            int d = 0;
            Map<String, Integer> classUseCount;

            while(itr.hasNext()) {
//                classUseCount = fanOutService.analyzePackage(directory, itr.next());
                d = fanOutService.analyzePackage2(className, itr.next());
            }

            wmcService = new WMCService();
            int e = wmcService.calculateWMC(className);

            numberOfMethodsService = new NumberOfMethodsService();
            int f = numberOfMethodsService.analyzePackage2(className);

            numberOfConstructorsService = new NumberOfConstructorsService();
            int g = numberOfConstructorsService.countConstructors(className);

            numberOfFieldsService = new NumberOfFieldsService();
            int h = numberOfFieldsService.countPutFields(className);

//            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c + ", FANOUT: " + d + ", WMC: " + e + ", NOM: " + f + ", NOC: " + g + ", NOF: " + h);
            System.out.println(className.replace(analysePath + "/", "") + " NOF: " + h + ", NOM: " + f + ", NoConstr: " + g + ", WMC: " + e + ", NoChild: " + a + ", DIT: " + b + ", LCOM: " + false + ", FANIN: " + c + ", FANOUT: " + d);

        }

        System.out.println();
        numberOfConstructorsService = new NumberOfConstructorsService();

//        System.out.println(numberOfConstructorsService.countConstructors("com/home/asm/Constructor"));

        numberOfFieldsService = new NumberOfFieldsService();
        System.out.println("numberOfFieldsService.countPutFields(): " + numberOfFieldsService.countPutFields("com/home/pureFabrication/fifthExample/Demo"));

        System.out.println();

        CohesionService cohesionService = new CohesionService();

        ClassReader classReader = new ClassReader("com/home/asm/CohesionExample");
        ClassMethodAnalyzer analyzer = new ClassMethodAnalyzer();
        classReader.accept(analyzer, 0);

        Map<String, Set<String>> methodToFields = analyzer.getMethodToFields();
//        cohesionService.getCohesion("com/home/asm/Fields");
        double a = CohesionService.calculateLCOM4(methodToFields);
        System.out.println("Cohesion: " + a);


//        System.out.println(numberOfFieldsService.countLocalVariablesMethods("com/home/asm/Fields"));



//        String className = "com/home/indirection/fourthAnalysis/fix/Book";
//        numberOfChildrenService = new NumberOfChildrenService(className);
//        int a = numberOfChildrenService.calculateNOC(analysePath);
////            System.out.println(className + " has " + a + " children");
//
//        depthOfInheritanceTree = new DepthOfInheritanceTree(className);
//        int b = depthOfInheritanceTree.calculateDIT();
////            System.out.println(className + " ,children: " + a + ", DIT: " + b);
//
//        fanInService = new FanInService(className, packagePath, directory);
//
//        Map<String, Integer> classCallCount = fanInService.analyzePackage(directory, className);
//
//        for(String name : classCallCount.keySet()) {
//            System.out.println(className + " ,call count: " + name + ", " + classCallCount.get(name));
//        }
//
//        int c = classCallCount.size();
//        System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c);

    }
}
