package com.home.polymorphism.thirdAnalysis;

public class ClassD extends ClassC {

    public ClassD(String name, int value) {
        super(name, value);
    }

    public static ClassD createClassD() {
        return new ClassD("Text", 20);
    }
}
