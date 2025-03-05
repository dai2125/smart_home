package com.home.creator.fix;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> input = List.of(
                Map.of("name", "Samsung", "price", 200, "count", 2),
                Map.of("name", "Apple", "price", 300, "count", 3),
                Map.of("name", "Lg", "price", 150, "count", 4)
        );
        var orders = new Orders(input);

        var totalSum = orders.getTotalPrice();

        System.out.println(totalSum);
    }
}