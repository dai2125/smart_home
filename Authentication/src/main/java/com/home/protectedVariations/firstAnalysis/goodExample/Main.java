package com.home.protectedVariations.firstAnalysis.goodExample;

public class Main {

    public static void main(String[] args) {
        PaymentMethod method = new CreditCardPayment();
        PaymentProcessor processor = new PaymentProcessor(method);
        processor.processPayment(100.0);
    }
}
