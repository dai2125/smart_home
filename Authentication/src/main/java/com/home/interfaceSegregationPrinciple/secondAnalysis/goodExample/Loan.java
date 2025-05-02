package com.home.interfaceSegregationPrinciple.secondAnalysis.goodExample;

public interface Loan extends Payment {

    void initiateLoanSettlement();
    void initiateRePayment();
}
