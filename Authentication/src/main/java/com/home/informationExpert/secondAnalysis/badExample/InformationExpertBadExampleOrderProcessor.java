package com.home.informationExpert.secondAnalysis.badExample;

public class InformationExpertBadExampleOrderProcessor {

    public double calculateToPrice(InformationExpertBadExampleOrder order) {
        return order.getItemCount() * order.getItemPrice();
    }
}
