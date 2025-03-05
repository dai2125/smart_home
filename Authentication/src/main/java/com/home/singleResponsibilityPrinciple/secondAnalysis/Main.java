package com.home.singleResponsibilityPrinciple.secondAnalysis;

public class Main {

    public static void main(String[] args) {

        Customer customer = new Customer();
        customer.setName("Dominik");
        customer.setAddress("Hohenems");

        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setItemName("Pizza");
        orderDetails.setQuantity(2);

        OrderManagement orderManagement = new OrderManagement(orderDetails, customer);
        orderManagement.prepareOrder();

        BillCalculation billCalculation = new BillCalculation(orderDetails);
        billCalculation.calculateBill();

        DeliveryApp deliveryApp = new DeliveryApp(orderManagement);
        deliveryApp.delivery();
    }
}
