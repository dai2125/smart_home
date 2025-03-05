package com.home.creator.fix;

import java.util.Map;

class Order {
    private final Product product;
    private final int count;

    public Order(Map<String, Object> order) {
        this.product = new Product(
                (String) order.get("name"),
                (int) order.get("price")
        );
        this.count = (int) order.get("count");
    }
    public int getTotalPrice() {
        return product.getPrice() * count;
    }
}