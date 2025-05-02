package com.home.cohesion2;

public class Example6 {

    int a = 4;
    int b = 7;
    int c = 1;
    int d = 1;
    int e = 2;
    int f = 3;
    int g = 5;
    int h = 8;

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
        System.out.println(true);
    }
}
