package com.home.InterfaceSegregationPrinciple.secondAnalysis.badExample;

import java.util.List;

public interface Payment {

    void initiatePayments();
    Object status();
    List<Object> getPayments();
    void initialLoanSettlement();
    void initiateRePayment();
}
