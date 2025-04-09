package com.home.creator.InitializingData.thirdExample;

public class ClassD {

    String a = "message";
    int b = 100;
    ClassC c1 = new ClassC(a, b);

    public ClassD() {
        ClassC c2 = new ClassC(a, b);
    }

    private ClassC one(String c, int d) {
        ClassC c3 = new ClassC(c, d);
        return c3;
    }
}
