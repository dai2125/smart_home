package com.home.polymorphism.secondAnalysis;

public class Main {

    public static void main(String[] args) {
        Parent parent;

        parent = new Child1();
        parent.print();

        parent = new Child2();
        parent.print();
    }
}
