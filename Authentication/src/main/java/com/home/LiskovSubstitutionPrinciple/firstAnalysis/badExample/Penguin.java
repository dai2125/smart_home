package com.home.LiskovSubstitutionPrinciple.firstAnalysis.badExample;

public class Penguin extends Bird {

    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins can't fly.");
    }
}
