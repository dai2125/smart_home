package com.home.protectedVariations.firstAnalysis.badExample;

public class PaymentProcessor {

    private CreditCardPayment creditCardPayment;
    private PayPalPayment payPalPayment;

    public PaymentProcessor() {
        this.creditCardPayment = new CreditCardPayment();
        this.payPalPayment = new PayPalPayment();
    }

    public void processPayment(double amount, String method) {
        if(method.equals("CreditCard")) {
            creditCardPayment.pay(amount);
        } else if(method.equals("PayPal")) {
            payPalPayment.pay(amount);
        } else {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
    }
}
