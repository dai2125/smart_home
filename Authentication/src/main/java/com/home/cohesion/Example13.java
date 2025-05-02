package com.home.cohesion;

public class Example13 {

    int var1 = 12;
    int var2 = 239;
    String var3 = "var3";
    String var4 = "var4";
    int a = 10;
    int b = 11;
    int c = 12;

    public void million(String var5) {
        if(var5.equals("var5")) {
            var1++;
        } else if(var5.equals("var6")) {
            var2++;
        } else if(var5.equals("var7")) {
            var1--;
        }

        for(int i = 0; i < var1; i++) {
            int j = i * i;
        }
    }

    public int twoMillion(int i) {
        if(i % 2 == 0) {
            i++;
        } else if(i % 3 == 0) {
            i *= 2;
        }

        for(int j = 0; j < i; j++) {
            if(j % 3 == 0) {
                return j *= 4;
            }
        }

        return 0;
    }

    public String threeMillion(String var1, String var2, String var3) {
        if(var1.length() == 0 || var2.length() == 0 || var3.length() == 0) {
            return "empty string";
        }
        if(var1.length() == 100 || var2.length() == 100 || var3.length() == 100) {
            return var1 + var2 + var3;
        }
        return "null";
    }

    public String fourMillion(int num) {
        switch(num) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            case 6:
                return "six";
            case 7:
                return "seven";
            default:
                return "null";
        }
    }

    public void five() {
        int a = 10;
        if(b < c) {
            a = b;
        } else {
            a = c;
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    private int six(int x, int y) {
        while(x != y) {
            if(x > y) {
                x = x -y;
            } else {
                y = y - x;
            }
        }
        return x;
    }
}
