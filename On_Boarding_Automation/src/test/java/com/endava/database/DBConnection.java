package com.endava.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private Connection connection = null;

    private void connect(){
        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();

        }

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql:postgres", "postgres",
                    "parola");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }
    }

    public List<String> getDbInfo(String query, String column) throws SQLException {
        connect();

        List<String> results = new ArrayList<>();

        if (connection != null) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String str = rs.getString(column);
                results.add(str);
            }
        } else {
            System.out.println("Failed to make connection!");
        }
        return results;
    }

    public List<String> getUserInfo(String query) throws SQLException {
        connect();

        List<String> results = new ArrayList<>();

        if (connection != null) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String field = rs.getString("first_name");
                results.add(field);
                field = rs.getString("last_name");
                results.add(field);
                field = rs.getString("endava_email");
                results.add(field);
                field = rs.getString("personal_email");
                results.add(field);
            }
        }
        else { System.out.println("Failed to make connection!"); }

        return results;
    }

    public static void main(String[] argv) throws SQLException {

        List<String> results = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        String query = "select distinct city from locations;";
        String column = "city";
        results = dbConnection.getDbInfo(query, column);
        System.out.println(results);
    }
}