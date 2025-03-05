package com.home.authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseService implements DatabaseQueries {

    private final Connection connection;

    // TODO Dependency Injection DI
    public DatabaseService(Connection connection) throws SQLException {
        this.connection = connection;
    }

    @Override
    public void registerUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            int rowInserted = preparedStatement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("User registered successfully");
            } else {
                System.out.println("User registration failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean loginUser(User user) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    System.out.println("Login successfully");
                    return true;
                } else {
                    System.out.println("Login failed");
                    return false;
                }
            }
        }
    }
}
