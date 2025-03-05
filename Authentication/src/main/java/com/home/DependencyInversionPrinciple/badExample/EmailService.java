package com.home.DependencyInversionPrinciple.badExample;

public class EmailService {

    public void sendMessage(String message) {
        System.out.println("Email sent: " + message);
    }
}
