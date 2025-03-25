package com.home.polymorphism.firstAnalysis.fix;

class SmsSender implements Sender {

    @Override
    public void send(String message) {
        System.out.println("Send sms message: " + message);
    }

}