package com.home.polymorphism.fix;

class SlackSender implements Sender {

    @Override
    public void send(String message) {
        System.out.println("Send slack message: " + message);
    }

}