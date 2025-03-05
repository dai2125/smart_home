package com.home.informationExpert.secondAnalysis.goodExample;

public class InformationExpertGoodExampleOrder {
    private int itemCount;
    private double itemPrice;

    public InformationExpertGoodExampleOrder(int itemCount, double itemPrice) {
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }

    public double calculateTotalPrice() {
        return itemCount * itemPrice;
    }
}
