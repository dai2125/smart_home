package com.home.coupling.thirdAnalysis.goodExample;

public class Subject {

    public static void main(String[] args) {
        Topic topic = new Topic1();
        Topic topic2 = new Topic2();
        topic.understand();
    }
}
