package com.sparta.insuranceclaim.adminLogin;

import java.sql.Connection;
import java.sql.SQLException;


public class adminLoginClass {
    private String username;
    private String password;


        public void loginChecker() {

            try (Connection connection = DatabaseHelper.getConnection()) {
                if (DatabaseHelper.isValidAdmin(connection, username, password)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid username or password. Login failed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}