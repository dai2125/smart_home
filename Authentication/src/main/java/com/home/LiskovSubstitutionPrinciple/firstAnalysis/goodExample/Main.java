package com.home.LiskovSubstitutionPrinciple.firstAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {
        BirdSanctuary sanctuary = new BirdSanctuary();
        Bird sparrow = new Sparrow();
        sanctuary.letBirdFly(sparrow);
    }
}
