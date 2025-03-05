package com.home.informationExpert.secondAnalysis.badExample;

public class InformationExpertBadExampleMain {

    public static void main(String[] args) {
        InformationExpertBadExampleOrder order = new InformationExpertBadExampleOrder(5, 20.0);
        InformationExpertBadExampleOrderProcessor processor = new InformationExpertBadExampleOrderProcessor();
        double total = processor.calculateToPrice(order);
        System.out.println("Total Price: " + total);
    }
}
