package com.home.indirection.thirdAnalysis.fix;

public class MessageMediator {

    public void sendMessage(String message, User sender, User receiver) {
        System.out.println(sender.getName() + " sends message to " + receiver.getName());
        receiver.receiveMessage(message);
    }
}
