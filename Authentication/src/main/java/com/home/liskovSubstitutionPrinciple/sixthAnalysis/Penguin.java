package com.home.liskovSubstitutionPrinciple.sixthAnalysis;

public class Penguin extends Bird {

    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins cant fly!");
    }
}
