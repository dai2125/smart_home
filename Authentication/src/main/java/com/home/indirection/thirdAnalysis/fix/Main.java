package com.home.indirection.thirdAnalysis.fix;

public class Main {

    public static void main(String[] args) {
        User user1 = new User("Alice");
        User user2 = new User("Bob");

        MessageMediator mediator = new MessageMediator();
        mediator.sendMessage("Hello, Bob!", user1, user2);
    }
}
