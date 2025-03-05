package com.home.creator.BHasTheInitalizingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA.goodExample;

public class DatabaseConnection {

    private String url;
    private String username;
    private String password;

    public DatabaseConnection(DatabaseConfig config) {
        this.url = config.getUrl();
        this.username = config.getUsername();
        this.password = config.getPassword();
    }

    public void connect() {
        System.out.println("Connecting to database at " + url + " with user " + username);
    }
}
