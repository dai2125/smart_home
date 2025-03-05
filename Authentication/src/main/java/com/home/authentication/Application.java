package com.home.authentication;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException {
        UserInput userInput = new UserInput();
        userInput.input();
    }
}
