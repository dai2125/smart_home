package com.home.InterfaceSegregationPrinciple.secondAnalysis.goodExample;

public interface Loan extends Payment {

    void initiateLoanSettlement();
    void initiateRePayment();
}
