package com.home.informationExpert.firstAnalysis.fix;

import java.util.List;

public class Orders {
    private final List<Order> orders;

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public int getTotalPrice() {
        return orders
                .stream()
                .mapToInt(Order::getTotalPrice)
                .sum();
    }
}
