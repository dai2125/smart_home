package com.home.cohesion;

public class Example10 {

    int a = 4;
    int b = 7;

    public int firstFunction() {
        return secondFunction();
    }

    public int secondFunction() {
        return a;
    }

    public int thirdFunction() {
        return b;
    }

    public void fourthFunction() {
        if(b > fifthFunction()) {
            b += b;
        } else {
            b -= b;
        }
    }

    public int fifthFunction() {
        return 100;
    }
}
