package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orclkish"; // Oracle DB URL
    private static final String USER = "SCOTT";
    private static final String PASSWORD = "kis03";

    // Method to establish a database connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection established successfully!");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    // Optional utility method to close connection safely
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error closing the database connection.");
                e.printStackTrace();
            }
        }
    }
}
