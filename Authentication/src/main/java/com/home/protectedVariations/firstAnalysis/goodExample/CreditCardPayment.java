package com.home.protectedVariations.firstAnalysis.goodExample;

public class CreditCardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}
