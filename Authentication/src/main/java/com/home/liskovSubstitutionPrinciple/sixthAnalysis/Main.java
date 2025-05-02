package com.home.liskovSubstitutionPrinciple.sixthAnalysis;

public class Main {

    public static void main(String[] args) {
        Bird bird1 = new Sparrow();
        Bird bird2 = new Penguin();
        bird1.fly();
        bird2.fly();
    }
}
