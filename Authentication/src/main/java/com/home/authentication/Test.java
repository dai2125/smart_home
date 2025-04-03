package com.home.authentication;

public class Test {


    String a = "here some information";
    int b = 1292;

    public String getText() {
        return "some text";
    }

    public String getReverseText(String input) {
        StringBuilder builder = new StringBuilder(input);
        return builder.reverse().toString();
    }

    public String getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
