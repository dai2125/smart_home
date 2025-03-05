package com.home.authentication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/registration";
        String user = "postgres";
        String password = "test";
        return DriverManager.getConnection(url, user, password);
    }
}
