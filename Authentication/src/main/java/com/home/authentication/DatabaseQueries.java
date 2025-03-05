package com.home.authentication;

import java.sql.SQLException;

// TODO Interface Segregation Principle ISP
public interface DatabaseQueries {
    void registerUser(User user);
    boolean loginUser(User user) throws SQLException;
}