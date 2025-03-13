package com.home.asm;

import java.util.Random;

public class Fields {

    private int a = 0;
    private int b;
    public String c = "String";
    protected String d;
    private int k;
    StringBuilder sb = new StringBuilder();
    Random random = new Random();

    public Fields() {
        int i = 1;
        boolean j = true;
    }

    public Fields(int a) {
        this.a = a;
    }

    private Fields(int a, String dromedar) {
        this.a = a;
        this.d = dromedar;
    }

    public int e() {
        return a;
    }

    protected void f(int o) {
        int g = o;
        System.out.println("g: " + g);
    }

    private boolean h() {
        for(int i = 0; i < c.length(); i++) {
            if(c.charAt(i) == 'a') {
                return true;
            }
        }
        int l = 5;
        int m = 700;
        b = l + m;
        int n = b * l * m;
        return false;
    }

    private String i() {
        for(int i = 0; i < 50; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}
