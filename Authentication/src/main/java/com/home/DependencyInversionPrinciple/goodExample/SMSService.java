package com.home.DependencyInversionPrinciple.goodExample;

public class SMSService implements MessageService {

    @Override
    public void sendMessage(String message) {
        System.out.println("SMS sent: " + message);
    }
}
