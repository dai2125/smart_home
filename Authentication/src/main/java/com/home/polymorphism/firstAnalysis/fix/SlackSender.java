package com.home.polymorphism.firstAnalysis.fix;

class SlackSender implements Sender {

    @Override
    public void send(String message) {
        System.out.println("Send slack message: " + message);
    }

}