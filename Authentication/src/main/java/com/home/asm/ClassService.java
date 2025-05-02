package com.home.asm;

import java.util.ArrayList;

// TODO DEPRECATED
public class ClassService {

    private static final ArrayList<InspectedClass> inspectedClasses = new ArrayList<InspectedClass>();

    private ClassService() {}

    public static void put(InspectedClass inspectedClass) {
        if(!listContainsClass(inspectedClass)) {
            inspectedClasses.add(inspectedClass);
        } else {
            updateClass(inspectedClass);
        }

//        if(!inspectedClasses.contains(inspectedClass)) {
//            inspectedClasses.add(inspectedClass);
//        } else {
//            inspectedClasses.remove(inspectedClass);
//            inspectedClasses.add(inspectedClass);
//        }
    }

    private static boolean listContainsClass(InspectedClass inspectedClass) {
        for(int i = 0; i < inspectedClasses.size(); i++) {
            if(inspectedClasses.get(i).getFullName().equals(inspectedClass.getFullName())) {
                return true;
            }
        }
        return false;
    }

    private static void updateClass(InspectedClass inspectedClass) {
        for(int i = 0; i < inspectedClasses.size(); i++) {
            if(inspectedClasses.get(i).getFullName().equals(inspectedClass.getFullName())) {
                inspectedClasses.get(i).setYalcom(inspectedClass.getYalcom());
                inspectedClasses.get(i).setLcom1(inspectedClass.getLcom1());
                inspectedClasses.get(i).setLcom2(inspectedClass.getLcom2());
                inspectedClasses.get(i).setLcom3(inspectedClass.getLcom3());
                inspectedClasses.get(i).setLcom4(inspectedClass.getLcom4());
                inspectedClasses.get(i).setLcom5(inspectedClass.getLcom5());

            }
        }
    }

    public static InspectedClass get(String className) {
        for(InspectedClass inspectedClass : inspectedClasses) {
//            System.out.println("ClassService: " + inspectedClass.getFullName() + ", " + inspectedClass.getName());
            if(inspectedClass.getName().equals(className)) {
                return inspectedClass;
            }
        }
        return null;
    }

    public static void remove(String className) {
        for(InspectedClass inspectedClass : inspectedClasses) {
            if(inspectedClass.getName().equals(className)) {
                inspectedClasses.remove(inspectedClass);
            }
        }
    }

    public static void clear() {
        inspectedClasses.clear();
    }

    public static void printAllClasses() {
        for(InspectedClass inspectedClass : inspectedClasses) {
            System.out.println(inspectedClass.getName() + ", getYalcom()" + inspectedClass.getYalcom() + ", getLcom4()" + inspectedClass.getLcom4());
        }
    }
}
