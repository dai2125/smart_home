package com.home.pureFabrication.fifthExample;

public interface PayStrategy {

    boolean pay(int paymentAmount);
    void collectPaymentDetails();
}
