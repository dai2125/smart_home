package com.home.polymorphism.thirdAnalysis;

public class ClassB extends ClassA {

    private String name;
    private int value;
    private boolean isValid;

    public ClassB(String name, int value) {
        super(name, value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value * 3;
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean two() {
        return value == 100;
    }
}
