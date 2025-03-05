package com.home.creator.mistake;

class Order {
    private final Product product;
    private final int count;

    public Order(Product product, int count) {
        this.product = product;
        this.count = count;
    }
    public int getTotalPrice() {
        return product.getPrice() * count;
    }
}