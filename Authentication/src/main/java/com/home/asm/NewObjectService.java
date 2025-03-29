package com.home.asm;

import java.util.ArrayList;

public class NewObjectService {

    private static final ArrayList<NewObject> objectList = new ArrayList<NewObject>();

    private NewObjectService() {}

    public static void put(NewObject object) {
        if(!objectList.contains(object)) {
            objectList.add(object);
        }
    }

    public static NewObject get(String className) {
        for(NewObject object : objectList) {
            if(object.getName().equals(className)) {
                return object;
            }
        }
        return null;
    }

    public static void remove(String className) {
        for(NewObject object : objectList) {
            if(object.getName().equals(className)) {
                objectList.remove(object);
            }
        }
    }

    public static void clear() {
        objectList.clear();
    }

    public static boolean contains(String name) {
        return objectList.contains(name);
    }
}
