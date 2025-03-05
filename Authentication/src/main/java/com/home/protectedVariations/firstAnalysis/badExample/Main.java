package com.home.protectedVariations.firstAnalysis.badExample;

public class Main {

    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();
        processor.processPayment(100.0, "CreditCard");
    }
}
