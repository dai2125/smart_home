package com.home.singleResponsibilityPrinciple.secondAnalysis;

import java.util.Random;

public class OrderDetails {

    private String orderId;
    private String itemName;
    private int quantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String itemName) {
        Random random = new Random();
        this.orderId = itemName + "-" + random.nextInt(500);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        setOrderId(itemName);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
