package com.home.dependencyInversionPrinciple.goodExample;

public class Notification {

    private final MessageService messageService;

    public Notification(MessageService messageService) {
        this.messageService = messageService;
    }

    public void notifyUser(String message) {
        messageService.sendMessage(message);
    }
}
