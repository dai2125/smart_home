package com.home.coupling.thirdAnalysis.badExample;

public class Subject {

    public static void main(String[] args) {
        Topic1 topic1 = new Topic1();
        Topic2 topic2 = new Topic2();
        topic1.understand();
        topic2.understand();
    }

}
