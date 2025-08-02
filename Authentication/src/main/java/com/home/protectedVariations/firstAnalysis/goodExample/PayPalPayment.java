package com.home.protectedVariations.firstAnalysis.goodExample;

public class PayPalPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}
