package com.home.cohesion.badExample;

public class Example {

    int a = 10;
    int b = 25;
    int c = 33;
    int d = 47;
    double e = 1020.10;
    float f = 1202.12f;
    String g = "String G";

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

    public int fifthFunction() {
        return 100;
    }

    public String sixthFunction() {
        return "sixthFunction";
    }

}
