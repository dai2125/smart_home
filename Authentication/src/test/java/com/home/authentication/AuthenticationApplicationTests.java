package com.home.authentication;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationApplicationTests {

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/registration",
                "postgres",
                "test"
        );

        clearUsersTable();
    }

    @AfterEach
    void breakUp() throws SQLException {
        if(connection != null) {
            connection.close();
        }
     }

    void clearUsersTable() throws SQLException {
        String sql = "DELETE FROM users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    @Test
    void registerUser() throws SQLException {
//        AuthenticationApplication.registerUser("Micheal", "20241111");

//        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1, "Michael");
//        preparedStatement.setString(2, "Muster");
//        preparedStatement.executeUpdate();
    }

    @Test
    void testCreateConnection() throws SQLException {
        try(Connection testConnection = AuthenticationApplication.createConnection()) {
            assertTrue(testConnection != null && !testConnection.isClosed(), "Die Verbindung sollte hergestellt werden");
        }
    }

    @Test
    void testRegisterUser() throws SQLException {
        AuthenticationApplication.registerUser("Michael", "20241111");

        String sql = "SELECT * FROM users WHERE username = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Michael");
//            statement.setString(2, "20241111");

            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next(), "Der Benutzer sollte in der Datenbank sein");
                assertEquals("Michael", resultSet.getString("username"));
                assertEquals("20241111", resultSet.getString("password"));

            }
        }
    }
}
