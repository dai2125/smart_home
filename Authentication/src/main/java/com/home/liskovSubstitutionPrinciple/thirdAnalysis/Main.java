package com.home.liskovSubstitutionPrinciple.thirdAnalysis;

public class Main {

    public static void main(String[] args) {
        BankAccount account = new CheckingAccount(100);

        System.out.println("account.getBalance() = " + account.getBalance());
        try {
            account.withdraw(98);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
//            throw new InsufficientFundsException(e);
        }
//        account.withdraw(97);
        System.out.println("account.getBalance() = " + account.getBalance());


    }
}
