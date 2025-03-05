package com.home.indirection.secondAnalysis.badExample;

public class AddressManager {

    private Customer customer;

    public AddressManager(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerStreet() {
        return customer.getAddress().getStreet();
    }

    public String getCustomerCity() {
        return customer.getAddress().getCity();
    }

    public String getCustomerZip() {
        return customer.getAddress().getZip();
    }
}
