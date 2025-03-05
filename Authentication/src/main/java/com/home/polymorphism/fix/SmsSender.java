package com.home.polymorphism.fix;

class SmsSender implements Sender {

    @Override
    public void send(String message) {
        System.out.println("Send sms message: " + message);
    }

}