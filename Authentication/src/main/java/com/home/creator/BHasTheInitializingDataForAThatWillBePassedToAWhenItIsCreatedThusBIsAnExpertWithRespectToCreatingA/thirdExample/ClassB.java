package com.home.creator.BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA.thirdExample;

public class ClassB {

    private String bString = "some bString information";
    private String text = "some text information";
    private String information = "some information";
    private int bInt = 4711;
    private int d = 42;
    private int e = 112358;

    private ClassA a;
//    private ClassA a = new ClassA(bString, bInt);

    public ClassB() {
        int f = 200;
        this.a = new ClassA(bString, 900);
        System.out.println(a.toString());
        int g = 300;
    }

    private void bMethod1() {
//        Class a = new ClassA(bString, bInt);
    }

//    private ClassA bMethod2() {
//        return new ClassA(bString, bInt);
//    }
}
