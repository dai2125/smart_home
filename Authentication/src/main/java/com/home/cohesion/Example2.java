package com.home.cohesion;

public class Example2 {

    int x = 47;
    int y = 11;

    public void a() {
        b();
    }

    public void b() {
        x = 20 + 20;
        y = 4 * 4;
    }

    public void c() {
        y = 12;
    }

    public void d() {
        y = 10;
        e();
    }

    public void e() {
        System.out.println("public void e");
    }

    public void f() {
        Example4 example4 = new Example4();
        System.out.println(example4.firstFunction());
    }
}
