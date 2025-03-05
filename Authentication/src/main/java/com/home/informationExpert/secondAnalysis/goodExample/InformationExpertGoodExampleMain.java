package com.home.informationExpert.secondAnalysis.goodExample;

public class InformationExpertGoodExampleMain {

    public static void main(String[] args) {
        InformationExpertGoodExampleOrder order = new InformationExpertGoodExampleOrder(5, 20.0);
        double total = order.calculateTotalPrice();
        System.out.println("Total Price: " + total);
    }
}
