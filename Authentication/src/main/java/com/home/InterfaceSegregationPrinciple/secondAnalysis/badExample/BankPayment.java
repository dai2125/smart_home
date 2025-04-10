package com.home.InterfaceSegregationPrinciple.secondAnalysis.badExample;

import java.util.ArrayList;
import java.util.List;

public class BankPayment implements Payment {

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

    @Override
    public void initialLoanSettlement() {
        throw new UnsupportedOperationException("Loan settlement is not applicable for bank payment");
    }

    @Override
    public void initiateRePayment() {
        throw new UnsupportedOperationException("Repayment is not applicable for bank payment");
    }

    public int getNumber() {
        return -1;
    }
}
