//package com.sparta.insuranceclaim;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class adminSearchClaims {
//
//    private String user = "";
//    private static final String DB_URL = "";
//    private static final String USER = "";
//    private static final String PASSWORD = "";
//
//    public ArrayList<String> claimSearch(Connection connection, String user){
//    ArrayList<String> userClaims = new ArrayList<String>();
//    String sql = "SELECT claims FROM Aegon WHERE username = " + user;
//
//    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//        userClaims.add(Arrays.toString(sql.split(",")));
//    } catch (SQLException e) {
//        throw new RuntimeException(e);
//
//    }
//        return userClaims;
//    }
//
//
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
//    }
//
//}
