package com.home.indirection.thirdAnalysis.mistake;

public class Main {

    public static void main(String[] args) {
        User user1 = new User("Alice");
        User user2 = new User("Bob");

        user1.sendMessage("Hello Bob!", user2);
    }
}
