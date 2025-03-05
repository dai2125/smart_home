package com.home.authentication;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;

public class AuthenticationApplication {

    static Scanner scanner = new Scanner(System.in);
    static String input = "";
    static String username;
    static String password;

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

    public static boolean loginUser(String username, String password) throws SQLException {
        Connection connection = createConnection();
          String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
        } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void test() {
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
    }

    public static int addition(int a, int b) {
        return a + b;
    }

    public static String getSomeText() {
        Test test = new Test();
//        int a = test.getAddition(12, 10);
        int b = 393;
//        int c = a * b;
        double[] d = new double[7];
        for (int i = 0; i < d.length; i++) {
            System.out.println(d[i]);
        }
        boolean e = false;
        e = true;
        test("test");
        String f = test.getText();
        String g = test.getReverseText("guz");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(f);
        stringBuilder.append(g);
        return "some Text";
    }

    public static void test(String text) {
        String a = getSomeText();
        int b = addition(12, 12);

    }

    public static void getInput() throws SQLException {
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
            } else if(input.equalsIgnoreCase("login")) {
                System.out.println("enter USERNAME");
                username = scanner.nextLine();
                System.out.println("enter PASSWORD");
                password = scanner.nextLine();
                System.out.println("username: " + username + " password: " + password);
                if(loginUser(username, password)) {
                    System.out.println("Logged in");
                } else {
                    System.out.println("check Credentials");
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {

        System.out.println("Authentication Application starts...");

        Test test = new Test();
//        test.getAddition(800, 320);
        test.getReverseText("zug");
        test.getText();
        test();
        int a = addition(25, 98);
        System.out.println(a);
        getSomeText();
        String b = test.getA();
        System.out.println(b);
        int c = test.getB();
        System.out.println(c);
        System.out.println(test.getA());
        System.out.println(test.getB());
        getInput();

    }
}
