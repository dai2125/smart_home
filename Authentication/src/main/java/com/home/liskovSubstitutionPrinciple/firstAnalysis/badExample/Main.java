package com.home.liskovSubstitutionPrinciple.firstAnalysis.badExample;

public class Main {

    public static void main(String[] args) {
        BirdSanctuary sanctuary = new BirdSanctuary();
        Bird penguin = new Penguin();
        sanctuary.letBirdFly(penguin);
    }
}
