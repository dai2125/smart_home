package com.home.creator.BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA.goodExample;

public class Main {

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig("jdbc:mysql://localhost:3306/mydb", "admin", "secret");
        DatabaseConnection connection = new DatabaseConnection(config);
        connection.connect();
    }
}
