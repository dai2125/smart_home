package com.home.indirection.secondAnalysis.goodExample;

public class Customer {
    private String name;
    private Address address;

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
