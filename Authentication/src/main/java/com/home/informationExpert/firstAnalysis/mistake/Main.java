package com.home.informationExpert.firstAnalysis.mistake;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        var orders = new Orders(List.of(
                new Order(new Product("Samsung", 200), 2),
                new Order(new Product("Apple", 300), 3),
                new Order(new Product("Lg", 150), 4)
        ));

        var totalSum = orders.getOrders()
                .stream()
                .mapToInt(order -> order.getProduct().getPrice() * order.getCount())
                .sum();

        System.out.println(totalSum);
    }
}
