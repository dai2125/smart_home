package com.home.pureFabrication.secondAnalysis.goodExample;

public class InputValidator {
    public boolean validateEmail(String email) {
        return email != null && email.contains("@");
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }
}

