package com.home.creator.BContainsOrCompositelyAggregatesA;

public class LCOM {

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
}
