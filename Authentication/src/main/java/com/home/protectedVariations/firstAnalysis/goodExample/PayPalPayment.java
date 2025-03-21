package com.home.protectedVariations.firstAnalysis.goodExample;

public class PayPalPayment implements PaymentMethod {

    private int error = 555;

    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using PayPal");
        if(a() == !a()) {
            System.out.println(a());
        }
        int b = 10 * 20;
        boolean c = false;
        error += 1000;
    }

    private boolean a() {
        return false;
    }
}
