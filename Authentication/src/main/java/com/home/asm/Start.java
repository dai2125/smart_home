package com.home.asm;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Start {

    public static void main(String[] args) throws IOException {

//        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\pureFabrication\\fifthExample";
//        String analysePath = "com/home/pureFabrication/fifthExample";
        String packagePath = "C:\\Users\\aigne\\IdeaProjects\\smart_home\\Authentication\\src\\main\\java\\com\\home\\DependencyInversionPrinciple\\goodExample";
        String analysePath = "com/home/DependencyInversionPrinciple/goodExample";
        String targetClass = "com/home/pureFabrication/fifthExample/PayByCreditCard";

        PackageService packageService = new PackageService();
        File directory = new File(packagePath);
        NumberOfChildrenService numberOfChildrenService;
        DepthOfInheritanceTree depthOfInheritanceTree;
        FanInService fanInService;
        FanOutService fanOutService;
        HashSet<String> test = packageService.allClasses(directory);

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

            System.out.println(className + " ,children: " + a + ", DIT: " + b + ", FANIN: " + c + ", FANOUT: " + d);


        }

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
