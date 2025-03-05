package com.home.pureFabrication.secondAnalysis.badExample;

public class User {
    private String name;
    private String email;
    private String phoneNumber;

    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean isValid() {
        return email != null && email.contains("@") &&
                phoneNumber != null && phoneNumber.matches("\\d{10}");
    }
}
