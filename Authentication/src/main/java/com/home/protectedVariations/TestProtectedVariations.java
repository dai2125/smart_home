package com.home.protectedVariations;

import com.home.protectedVariations.firstAnalysis.goodExample.CreditCardPayment;
import com.home.protectedVariations.firstAnalysis.goodExample.PayPalPayment;
import com.home.protectedVariations.firstAnalysis.goodExample.PaymentMethod;
import com.home.protectedVariations.firstAnalysis.goodExample.PaymentProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class TestProtectedVariations {

    @Test
    public void test() {
        PaymentMethod paymentMethod = Mockito.mock(PaymentMethod.class);
        PaymentProcessor paymentProcessor = new PaymentProcessor(paymentMethod);

        double amount = 4711.0;

        paymentProcessor.processPayment(amount);

        verify(paymentMethod).pay(amount);

        verifyNoMoreInteractions(paymentMethod);

        paymentMethod = new CreditCardPayment();
        paymentProcessor = new PaymentProcessor(paymentMethod);

        paymentProcessor.processPayment(amount);

        paymentMethod = new PayPalPayment();
        paymentProcessor = new PaymentProcessor(paymentMethod);

        paymentProcessor.processPayment(amount);
    }

}
