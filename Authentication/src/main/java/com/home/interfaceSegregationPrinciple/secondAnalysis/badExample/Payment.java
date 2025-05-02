package com.home.interfaceSegregationPrinciple.secondAnalysis.badExample;

import java.util.List;

public interface Payment {

    void initiatePayments();
    Object status();
    List<Object> getPayments();
    void initialLoanSettlement();
    void initiateRePayment();
}
