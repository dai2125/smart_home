package com.home.asm;

public class LCOMIs2 {

    int x = 47;
    int y = 11;

    public void a() {
        System.out.println(b());
    }

    public int b() {
        return x;
    }

    public void c() {
        System.out.println(y);
    }

    public void d() {
        e(y);
    }

    public void e(int something) {
        System.out.println(something);
    }
}
