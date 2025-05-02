package com.home.polymorphism.thirdAnalysis;

public class Main {

    public static void main(String[] args) {
        ClassP p = new ClassP();

        p.one();
        System.out.println(p.one());
        p.two();
        System.out.println(p.three());
        System.out.println(p.four());

        ClassD classD = new ClassD("D", 1);
        classD.getName();
        classD.getValue();
        classD.isValid();
        classD.three();
    }
}
