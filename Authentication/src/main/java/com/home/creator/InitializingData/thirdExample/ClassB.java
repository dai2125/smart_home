package com.home.creator.InitializingData.thirdExample;

import java.util.Random;

public class ClassB {

    private String bString = "some bString information";
    private String text = "some text information";
    private String information = "some information";
    private int bInt = 4711;
    private int d = 42;
    private int e = 112358;
    private String f = "some text " + bString;

    private ClassA a;
    private ClassA z = new ClassA(bString, bInt);
    private ClassC c = new ClassC(bString, bInt);
    private ClassC c1 = new ClassC("information", 200);
    private ClassC c2;

    public ClassB(String message, int num) {
        int f = 200;
        this.a = new ClassA(bString, 900); // TODO
        this.c2 = new ClassC(message, 100);
        System.out.println(a.toString());
        int g = 300;
    }

    private void one() {
        a = new ClassA(bString, bInt);
        Random random = new Random();
        System.out.println(true);
        StringBuilder sb = new StringBuilder();
        sb.append(false);
        System.out.println(sb.toString());
    }

    private ClassA two() {
        return new ClassA(bString, bInt);
    }

    public String three(String text, int count) {
        ClassA aThree = new ClassA(text, count);
        return aThree.toString();
    }

    public void four() {
        String message = "message";
        int time = 100;

        ClassA aFour = new ClassA(message, time);
        aFour.toString();
    }

    public boolean five() {
        ClassA aFive = new ClassA("description", 4711);
        return aFive.equals(bString);
    }

    public ClassC six() {
        ClassC c2 = new ClassC(bString, bInt);
        return c2;
    }

    public ClassC seven(String alpha, int beta) {
        ClassC c3 = new ClassC(alpha, beta);
        return c3;
    }

    public ClassC eight() {
        ClassC c4 = new ClassC("fhv", 4711);
        return c4;
    }

    public ClassC nine(String param) {
        ClassC c5 = new ClassC(param, 4711);
        return c5;
    }

    public ClassC ten(int value) {
        ClassC c6 = new ClassC(bString, value);
        return c6;
    }

    protected final int eleven() {
        return 0;
    }

    private static int twelve() {
        ClassA a1 = new ClassA("xxx", -2);
        return -1;
    }

    private final static int thirteen() {
        return -1;
    }

    final int fourteen() {
        return -1;
    }

    void fifteen() {
        System.out.println();
    }

    private ClassC sixteen(String param1, int param2) {
        ClassC c = new ClassC(param1, param2);
        return c;
    }
}
