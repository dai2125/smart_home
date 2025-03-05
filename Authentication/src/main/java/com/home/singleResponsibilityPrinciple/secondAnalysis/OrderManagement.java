package com.home.singleResponsibilityPrinciple.secondAnalysis;

public class OrderManagement {

    private Customer customer;
    private OrderDetails orderDetails;

    public OrderManagement(OrderDetails orderDetails, Customer customer) {
        this.orderDetails = orderDetails;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void prepareOrder() {
        System.out.println("Preparing order for customer - " + this.customer.getName() + " who has ordered " + this.orderDetails.getItemName());
    }
}
