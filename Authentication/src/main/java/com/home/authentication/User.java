package com.home.authentication;

import java.sql.SQLException;

public class User {

    private String username;
    private String password;
    UserInput userInput;

    public User(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
        userInput = new UserInput();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
