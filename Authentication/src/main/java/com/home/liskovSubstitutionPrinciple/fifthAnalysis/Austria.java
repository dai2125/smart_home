package com.home.liskovSubstitutionPrinciple.fifthAnalysis;

public class Austria extends Country {
    public Austria(String name, String currency, double area, int population, int gdp) {
        super(name, currency, area, population, gdp);
    }

    public boolean a() {
        return false;
    }

    private String b(String c) {
        return c + " : " ;
    }
}
