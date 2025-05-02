package com.home.dependencyInversionPrinciple.badExample;

public class EmailService {

    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}
