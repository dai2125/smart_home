package com.home.pureFabrication.secondAnalysis.goodExample;

public class Main {
    public static void main(String[] args) {
        InputValidator validator = new InputValidator();
        User user = new User("John", "john@example.com", "1234567890");

        if(validator.validateEmail(user.getEmail()) && validator.validatePhoneNumber(user.getPhoneNumber())) {
            System.out.println("User is valid");
        } else {
            System.out.println("Invalid user details.");
        }
    }
}
