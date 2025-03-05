package com.home.indirection.secondAnalysis.badExample;

public class Main {
    public static void main(String[] args) {
        Address address = new Address("Main St.", "Springfield", "12345");
        Customer customer = new Customer("John Doe", address);
        AddressManager manager = new AddressManager(customer);

        System.out.println("Street: " + manager.getCustomerStreet());
        System.out.println("City: " + manager.getCustomerCity());
        System.out.println("ZIP: " + manager.getCustomerZip());
    }
}
