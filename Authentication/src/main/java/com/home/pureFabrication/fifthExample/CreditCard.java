package com.home.pureFabrication.fifthExample;

public class CreditCard {

    private int amount;
    private String number;
    private String date;
    private String cvc;

    CreditCard(String number, String date, String cvc) {
        this.amount = 100_000;
        this.number = number;
        this.date = date;
        this.cvc = cvc;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
