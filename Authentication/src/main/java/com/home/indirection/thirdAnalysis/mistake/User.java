package com.home.indirection.thirdAnalysis.mistake;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String message, User receiver) {
        System.out.println(name + " sends message to " + receiver.getName() + ": " + message);
        receiver.receiveMessage(message);
    }

    public void receiveMessage(String message) {
        System.out.println(name + " received: " + message);
    }
}
