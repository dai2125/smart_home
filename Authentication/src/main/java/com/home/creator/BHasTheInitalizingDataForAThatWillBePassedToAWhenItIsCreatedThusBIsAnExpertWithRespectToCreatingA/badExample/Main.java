package com.home.creator.BHasTheInitalizingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA.badExample;

public class    Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "admin";
        String password = "secret";

        DatabaseConnection connection = new DatabaseConnection(url, username, password);
        connection.connect();
    }
}
