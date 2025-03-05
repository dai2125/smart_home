package com.home.LiskovSubstitutionPrinciple.fifthAnalysis;

public class Country {

    private String name;
    private String currency;
    private double area;
    private int population;
    private int gdp;

    public Country(String name, String currency, double area, int population, int gdp) {
        this.name = name;
        this.currency = currency;
        this.area = area;
        this.population = population;
        this.gdp = gdp;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public double getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public int getGdp() {
        return gdp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setGdp(int gdp) {
        this.gdp = gdp;
    }
}
