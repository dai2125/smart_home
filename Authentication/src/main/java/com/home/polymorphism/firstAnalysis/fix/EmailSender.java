package com.home.polymorphism.firstAnalysis.fix;

class EmailSender implements Sender {

    @Override
    public void send(String message) {
        System.out.println("Send email message: " + message);
    }

}