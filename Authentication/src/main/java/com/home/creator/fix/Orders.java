package com.home.creator.fix;

import java.util.List;
import java.util.Map;

class Orders {
    private final List<Order> orders;

    public Orders(List<Map<String, Object>> orders) {
        this.orders = orders
                .stream()
                .map(Order::new)
                .toList();
    }

    public int getTotalPrice() {
        return orders
                .stream()
                .mapToInt(Order::getTotalPrice)
                .sum();
    }
}