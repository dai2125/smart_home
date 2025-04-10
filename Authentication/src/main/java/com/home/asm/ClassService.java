package com.home.asm;

import java.util.ArrayList;

public class ClassService {

    private static final ArrayList<InspectedClass> inspectedClasses = new ArrayList<InspectedClass>();

    private ClassService() {}

    public static void put(InspectedClass inspectedClass) {
        if(!inspectedClasses.contains(inspectedClass)) {
            inspectedClasses.add(inspectedClass);
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
}
