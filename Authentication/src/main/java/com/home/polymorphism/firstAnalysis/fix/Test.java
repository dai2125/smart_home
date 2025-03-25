package com.home.polymorphism.firstAnalysis.fix;

public class Test {

//    private int something(int x, int y) {
//        while(x != y) {
//            if(x > y) {
//                x = x - y;
//            } else {
//                y = y - x;
//            }
//        }
//        return x;
//    }

    private void hurra(int a, int b, int c) {
        a = 10;
        if(b > c) {
            a = b;
        } else {
            a = c;
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}
