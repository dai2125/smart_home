package com.home.interfaceSegregationPrinciple.secondAnalysis.badExample;

import java.util.ArrayList;
import java.util.List;

public class LoanPayment implements Payment {

    private String loanId;
    private double loanAmount;
    private double interestRate;
    private List<String> paymentHistory;

    public LoanPayment(String loanId, double loanAmount, double interestRate) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.paymentHistory = new ArrayList<String>();
    }

    @Override
    public void initiatePayments() {
        throw new UnsupportedOperationException("This is not a bank payment");
    }

    @Override
    public Object status() {
        return null;
    }

    @Override
    public List<Object> getPayments() {
        return new ArrayList<>(paymentHistory);
    }

    @Override
    public void initialLoanSettlement() {
        System.out.println("Loan ID: " + loanId + " has been settled with an initial payment.");
        loanAmount -= 1000;
        paymentHistory.add("Initial settlement of €1000");
    }

    @Override
    public void initiateRePayment() {
        System.out.println("Repayment initiated for loan ID: " + loanId);
        double repaymentAmount = loanAmount * interestRate;
        paymentHistory.add("Repayment of € " + repaymentAmount);
    }
}
