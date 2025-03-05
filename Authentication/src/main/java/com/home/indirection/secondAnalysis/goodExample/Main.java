package com.home.indirection.secondAnalysis.goodExample;

public class Main {
    public static void main(String[] args) {
        Address address = new Address("Main St.", "Springfield", "12345");
        Customer customer = new Customer("John Doe", address);
        CustomerService service = new CustomerService();

        String fullAddress = service.getCustomerFullAddress(customer);
        System.out.println("Full Address: " + fullAddress);
    }
}
