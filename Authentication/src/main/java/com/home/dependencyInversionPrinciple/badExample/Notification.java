package com.home.dependencyInversionPrinciple.badExample;

public class Notification {

    private EmailService emailService;

    public Notification() {
        this.emailService = new EmailService();
    }

    public void notifyUser(String message) {
        emailService.sendMessage(message);
    }
}
