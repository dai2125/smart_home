package com.home.LiskovSubstitutionPrinciple.fourthAnalysis;

public class Person {

    private int age;

    public Person(int age) {
        if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age");
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        if(age < 0 || age > 150) throw new IllegalArgumentException("Invalid age");
        this.age = age;
    }
}
