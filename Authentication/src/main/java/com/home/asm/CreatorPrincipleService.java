package com.home.asm;

import java.util.ArrayList;

public class CreatorPrincipleService {

    private static final ArrayList<CreatorPrinciple> creatorPrincipleList = new ArrayList<CreatorPrinciple>();

    private CreatorPrincipleService() {}

    public static void put(CreatorPrinciple creatorPrinciple) {
//        if(!creatorPrincipleList.contains(creatorPrinciple)) {
//            creatorPrincipleList.add(creatorPrinciple);
//        }
        if(!contains(creatorPrinciple.getName())) {
            creatorPrincipleList.add(creatorPrinciple);
        }
    }

    public static CreatorPrinciple get(String className) {
//        System.out.println("1: ");
        for(CreatorPrinciple creatorPrinciple : creatorPrincipleList) {
//            System.out.println("2:" + creatorPrinciple.getName());
//            System.out.println("2:" + className);

            if(creatorPrinciple.getName().equals(className)) {
//                System.out.println("3: ");

                return creatorPrinciple;
            }
        }
        return null;
    }

    public static void remove(String className) {
        for(CreatorPrinciple creatorPrinciple : creatorPrincipleList) {
            if(creatorPrinciple.getName().equals(className)) {
                creatorPrincipleList.remove(creatorPrinciple);
            }
        }
    }

    public static void clear() {
        creatorPrincipleList.clear();
    }

    public static boolean contains(String name) {
//        return creatorPrincipleList.contains(name);
        for(int i = 0; i < creatorPrincipleList.size(); i++) {
            if(creatorPrincipleList.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int printAll() {
        System.out.println("PRINT ALL");
        for(int i = 0; i < creatorPrincipleList.size(); i++) {
            System.out.println(//creatorPrincipleList.get(i).getName() + " "
                                creatorPrincipleList.get(i).toString());
        }
        return 0;
    }
}
