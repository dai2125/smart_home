package com.home.creator.mistake;

import java.util.List;

class Orders {
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