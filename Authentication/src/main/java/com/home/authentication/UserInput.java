package com.home.authentication;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInput {

    private Scanner scanner = new Scanner(System.in);
    private String input = "";
    private Connection connection = DatabaseConnection.createConnection();
    private DatabaseService databaseService = new DatabaseService(connection);

    public UserInput() throws SQLException {
    }

    public void input() throws SQLException {
        while (true) {
            System.out.print("LOGIN or REGISTER:");
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("register")) {
                System.out.println("Enter your name");
                String name = scanner.nextLine();
                System.out.println("Enter your password");
                String password = scanner.nextLine();
                User user = new User(name, password);
                databaseService.registerUser(user);
            } else if (input.equalsIgnoreCase("login")) {
                System.out.println("Enter your name");
                String name = scanner.nextLine();
                System.out.println("Enter your password");
                String password = scanner.nextLine();
                User user = new User(name, password);
                if(databaseService.loginUser(user)) {
                    System.out.println("You are logged in");
                    System.out.println("What you like to do?");
                }
            }
        }
    }
}
