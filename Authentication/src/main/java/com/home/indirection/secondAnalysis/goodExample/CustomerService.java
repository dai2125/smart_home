package com.home.indirection.secondAnalysis.goodExample;

public class CustomerService {
    public String getCustomerFullAddress(Customer customer) {
        Address address = customer.getAddress();
        return address.getStreet() + ", " + address.getCity() + " " + address.getZip();
    }
}
