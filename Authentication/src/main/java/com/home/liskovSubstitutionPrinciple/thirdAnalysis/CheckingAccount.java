package com.home.liskovSubstitutionPrinciple.thirdAnalysis;

class CheckingAccount extends BankAccount {
    public CheckingAccount(double initialBalance) {
        super(initialBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        super.withdraw(amount);  // Basismethode aufrufen
    }
}
