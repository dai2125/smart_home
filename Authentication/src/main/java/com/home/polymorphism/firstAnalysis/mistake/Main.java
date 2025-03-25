package com.home.polymorphism.firstAnalysis.mistake;

public class Main {

    enum SenderType {
        EMAIL, SMS, SLACK
    }

    public static void main(String[] args) {
        var message = "Test message";
        var senderType = SenderType.EMAIL;

        send(message, senderType);
    }

    static void send(String message, SenderType senderType) {
        switch (senderType) {
            case EMAIL -> new EmailSender().send(message);
            case SMS -> new SmsSender().send(message);
            case SLACK -> new SlackSender().send(message);
        }
    }

}