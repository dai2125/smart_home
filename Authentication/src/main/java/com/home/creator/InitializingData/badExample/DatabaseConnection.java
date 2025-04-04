package com.home.creator.InitializingData.badExample;

public class DatabaseConnection {

    private String url;
    private String username;
    private String password;

    public DatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        System.out.println("Connecting to database at " + url + " with user " + username);
    }
}
