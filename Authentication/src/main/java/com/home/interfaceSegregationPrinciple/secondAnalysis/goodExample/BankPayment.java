package com.home.interfaceSegregationPrinciple.secondAnalysis.goodExample;

import java.util.ArrayList;
import java.util.List;

public class BankPayment implements Bank {

    private String accountId;
    private double balance;
    private List<String> paymentHistory;

    public BankPayment(String accountId, double balance, List<String> paymentHistory) {
        this.accountId = accountId;
        this.balance = balance;
        this.paymentHistory = new ArrayList<>();
    }

    @Override
    public void initiatePayments() {
        System.out.println("Initiating payment for account ID: " + accountId);
    }

    @Override
    public Object status() {
        return "Account ID: " + accountId + ", Current Balance: " + balance;
    }

    @Override
    public List<Object> getPayments() {
        return new ArrayList<>(paymentHistory);
    }
}
