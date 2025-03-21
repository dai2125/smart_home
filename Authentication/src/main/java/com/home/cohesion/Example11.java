package com.home.cohesion;

public class Example11 {

    int x = 4;
    int y = 7;

    public int A() {
        return B();
    }

    public int B() {
        return x;
    }

    public int C() {
        return x + y;
    }

    public void D() {
        if(y > E()) {
            y += y;
        } else {
            y -= y;
        }
    }

    public int E() {
        return 100;
    }
}
