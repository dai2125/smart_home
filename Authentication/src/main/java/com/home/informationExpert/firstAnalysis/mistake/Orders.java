package com.home.informationExpert.firstAnalysis.mistake;

import java.util.List;

public class Orders {
    private final List<Order> orders;

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
