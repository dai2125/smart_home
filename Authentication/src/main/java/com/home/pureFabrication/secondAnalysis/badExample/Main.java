package com.home.pureFabrication.secondAnalysis.badExample;

public class Main {
    public static void main(String[] args) {
        User user = new User("John", "john@example.com", "1234567890");

        if (user.isValid()) {
            System.out.println("User is valid!");
        } else {
            System.out.println("Invalid user details.");
        }
    }
}
