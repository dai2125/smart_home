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

    private ClassA classA1;
    private ClassA classA2 = new ClassA(bString, bInt);
    private ClassC classC1 = new ClassC(bString, bInt);
    private ClassC classC2 = new ClassC("information", 200);
    private ClassC classC3;

    public ClassB(String message, int num) {
        int f = 200;
        this.classA1 = new ClassA(bString, 900);
        this.classC3 = new ClassC(message, 100);
        System.out.println(classA1.toString());
        int g = 300;
    }

    private void one() {
        classA2 = new ClassA(bString, bInt);
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
        ClassA classA3 = new ClassA(text, count);
        return classA3.toString();
    }

    public void four() {
        String message = "message";
        int time = 100;

        ClassA classA4 = new ClassA(message, time);
        classA4.toString();
    }

    public boolean five() {
        ClassA classA5 = new ClassA("description", 4711);
        return classA5.equals(bString);
    }

    public ClassC six() {
        ClassC classC4 = new ClassC(bString, bInt);
        return classC4;
    }

    public ClassC seven(String alpha, int beta) {
        ClassC classC5 = new ClassC(alpha, beta);
        return classC5;
    }

    public ClassC eight() {
        ClassC classC6 = new ClassC("fhv", 4711);
        return classC6;
    }

    public ClassC nine(String param) {
        ClassC classC7 = new ClassC(param, 4711);
        return classC7;
    }

    public ClassC ten(int value) {
        ClassC classC8 = new ClassC(bString, value);
        return classC8;
    }

    protected final int eleven() {
        return 0;
    }

    private static int twelve() {
        ClassA classA5 = new ClassA("xxx", -2);
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
        ClassC classC9 = new ClassC(param1, param2);
        return classC9;
    }
}
