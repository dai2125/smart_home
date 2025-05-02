package com.home.liskovSubstitutionPrinciple.fourthAnalysis;

public class Employee extends Person {

    private String position;
    private double salary;
    private static final int TWELVE_MONTHS = 12;

    public Employee(int age, String position, double salary) {
        super(age);
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double calculateYearlySalary() {
        return salary * TWELVE_MONTHS;
    }
}
