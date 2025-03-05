package com.home.polymorphism.fix;

import java.util.EnumMap;
import java.util.Map;

public class Main {

    enum SenderType {
        EMAIL, SMS, SLACK
    }

    private static final Map<SenderType, Sender> SENDERS = new EnumMap<>(SenderType.class);

    static {
        SENDERS.put(SenderType.EMAIL, new EmailSender());
        SENDERS.put(SenderType.SMS, new SmsSender());
        SENDERS.put(SenderType.SLACK, new SlackSender());
    }

    public static void main(String[] args) {
        var message = "Test message";
        var senderType = SenderType.EMAIL;

        send(message, senderType);
    }

    static void send(String message, SenderType senderType) {
        SENDERS.get(senderType).send(message);
    }

}