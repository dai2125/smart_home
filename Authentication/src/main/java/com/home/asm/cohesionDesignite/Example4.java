package com.home.asm.cohesionDesignite;

public class Example4 {

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

    public int fifthFunction() {
        return 4711;
    }

    public int sixthFunction() {
        return 0;
    }

    public boolean seventhFunction() {
        return true;
    }
}
