package com.home.authentication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AuthenticationApplication {

    Connection connection;

    public static Connection createConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/registration";
        String user = "postgres";
        String password = "test";
        return DriverManager.getConnection(url, user, password);
    }

    public static void registerUser(String username, String password) throws SQLException {
        Connection connection = createConnection();
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            int rowInserted = statement.executeUpdate();
            if(rowInserted > 0) {
                System.out.println("Nutzer erfolgreich registriert");
            }
        } catch (SQLException e) {
            System.out.println("Fehler bei der Registrierung: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {

        System.out.println("Authentication Application starts...");

        try (Connection connection = createConnection()) {
            if (connection != null) {
                System.out.println("Datenbankverbindung erfolgreich hergestellt");
            } else {
                System.out.println("Fehler: Datenbankverbindung konnte nicht hergestellt werden");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Datenbankfehler: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String input = "";
        String username;
        String password;
        while(!input.equalsIgnoreCase("exit")) {
            System.out.println("enter REGISTER or LOGIN");
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("register")) {
                System.out.println("enter USERNAME");
                username = scanner.nextLine();
                System.out.println("enter PASSWORD");
                password = scanner.nextLine();
                System.out.println("username: " + username + " password: " + password);
                registerUser(username, password);
            }
        }
    }
}
