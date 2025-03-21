package com.home.cohesion;

public class Example5 {

    int a = 4;
    int b = 7;
    int c = 1;
    int d = 1;

    public int firstFunction() {
        return a;
    }

    public int secondFunction() {
        return a + b;
    }

    public int thirdFunction() {
        return b + c;
    }

    public int fourthFunction() {
        return d;
    }

    public void fifthFunction() {
        System.out.println(sixthFunction());
    }

    public int sixthFunction() {
        return seventhFunction();
    }

    public int seventhFunction() {
        return 1;
    }
}
