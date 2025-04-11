// DatabaseConnection.java
package org.keyin.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles PostgreSQL database connection for the application.
 * Centralizes access credentials and connection logic.
 */
public class DatabaseConnection {
    // You only need to change the name of the database in the URL, unless PG runs on another port on your system. Default port is 5432 
    private static final String URL = "jdbc:postgresql://localhost:5432/gym_app";
//    By default the username is postgres and the password is what ever you set it to be. I usually keep mine simple
    private static final String USER = "postgres";
    private static final String PASSWORD = "Keyin2024";

    /**
     * Attempts to create a live database connection.
     *
     * @return a JDBC Connection object
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection() throws SQLException, SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Main method to quickly test DB connectivity from the console.
     */
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            System.out.println("Connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
