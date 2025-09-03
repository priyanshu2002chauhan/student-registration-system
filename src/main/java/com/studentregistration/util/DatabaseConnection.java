package com.studentregistration.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class implementing Singleton pattern
 * Manages database connections and provides connection pooling concept
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/student_registration_db";
    private static final String USERNAME = "root"; // Change as per your MySQL setup
    private static final String PASSWORD = "password"; // Change as per your MySQL setup

    private static DatabaseConnection instance;
    private Connection connection;

    // Private constructor for Singleton pattern
    private DatabaseConnection() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to create database connection!");
            e.printStackTrace();
        }
    }

    /**
     * Get singleton instance of DatabaseConnection
     * @return DatabaseConnection instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection is closed or invalid
     */
    public Connection getConnection() throws SQLException {
        // Check if connection is closed or invalid, create new one
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection!");
            e.printStackTrace();
        }
    }

    /**
     * Test database connectivity
     * @return true if connection is valid, false otherwise
     */
    public boolean testConnection() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Get database connection parameters for configuration
     * @return connection info string
     */
    public String getConnectionInfo() {
        return "URL: " + URL + ", Username: " + USERNAME;
    }
}
