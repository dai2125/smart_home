package com.home.cohesion;

public class Example9 {

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
        return a + b + c;
    }

    public int fourthFunction() {
        return a + b + d;
    }

    public int fifthFunction() {
        return a + b + c + d;
    }

    public int sixthFunction() {
        return a + b + c + d;
    }

    public boolean seventhFunction() {
        return a - b - c - d <= 7;
    }

    public void eightFunction() {
        System.out.println(a + b + c * d);
    }

    public void nineFunction(int d) {
        System.out.println(d);
    }
}
