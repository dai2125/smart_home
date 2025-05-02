package com.home.dependencyInversionPrinciple.goodExample;

public class Main {

    public static void main(String[] args) {
        MessageService emailService = new EmailService();
        Notification notification = new Notification(emailService);
        notification.notifyUser("Hello via Email!");

        MessageService smsService = new SMSService();
        Notification smsNotification = new Notification(smsService);
        smsNotification.notifyUser("Hello via SMS!");
    }
}
