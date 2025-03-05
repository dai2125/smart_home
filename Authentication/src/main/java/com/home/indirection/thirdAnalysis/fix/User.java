package com.home.indirection.thirdAnalysis.fix;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void receiveMessage(String message) {
        System.out.println(name + " received: " + message);
    }
}
