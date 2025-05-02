package com.home.liskovSubstitutionPrinciple.thirdAnalysis;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) throw new IllegalArgumentException("Balance cannot be negative");
        this.balance = initialBalance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > balance) throw new InsufficientFundsException("Not enough funds");
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}

