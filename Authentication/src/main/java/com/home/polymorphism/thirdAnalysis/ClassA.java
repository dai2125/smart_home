package com.home.polymorphism.thirdAnalysis;

public class ClassA {

    private String name;
    private int value;

    public ClassA(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value * 2;
    }

    public boolean one() {
        return value == 1;
    }
}
