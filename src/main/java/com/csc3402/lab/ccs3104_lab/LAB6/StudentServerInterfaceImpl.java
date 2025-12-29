// File: com/example/rmi/server/StudentServerInterfaceImpl.java
package com.csc3402.lab.ccs3104_lab.LAB6;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class StudentServerInterfaceImpl extends UnicastRemoteObject implements StudentServerInterface {
    private Connection connection;

    // Update these values to match your Oracle database settings
    private static final String DB_URL = "jdbc:oracle:thin:@fsktmdbora.upm.edu.my:1521:fsktm";  // Change to your Oracle host/port/SID
    private static final String DB_USER = "A215625";  // Your Oracle username
    private static final String DB_PASSWORD = "215625";  // Your Oracle password

    public StudentServerInterfaceImpl() throws RemoteException {
        super();
        initializeJDBC();
    }

    private void createTableIfNotExists() throws SQLException {
        // Check if table exists in Oracle
        String checkTableSQL = "SELECT COUNT(*) FROM user_tables WHERE table_name = 'SCORES'";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(checkTableSQL)) {
            if (rs.next() && rs.getInt(1) == 0) {
                // Table doesn't exist, create it
                String createTableSQL = "CREATE TABLE Scores (" +
                        "name VARCHAR2(50), " +
                        "score NUMBER(5,2), " +
                        "permission NUMBER(1))";
                stmt.execute(createTableSQL);
                System.out.println("Scores table created.");

                // Insert sample data
                stmt.executeUpdate("INSERT INTO Scores VALUES ('John', 90.5, 1)");
                stmt.executeUpdate("INSERT INTO Scores VALUES ('Michael', 100, 1)");
                stmt.executeUpdate("INSERT INTO Scores VALUES ('Michelle', 100, 0)");

                connection.commit();
                System.out.println("Sample data inserted.");
            } else {
                System.out.println("Scores table already exists.");
            }
        }
    }

    private void initializeJDBC() throws RemoteException {
        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Oracle JDBC Driver loaded");

            // Connect to Oracle database
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Connected to Oracle database");

            createTableIfNotExists();
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Oracle JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RemoteException("Database connection error", e);
        }
    }
    @Override
    public double findScore(String name) throws RemoteException {
        String query = "SELECT score, permission FROM Scores WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Oracle uses NUMBER(1) for boolean: 1 = true, 0 = false
                    int permissionValue = resultSet.getInt("permission");
                    boolean permission = (permissionValue == 1);
                    if (permission) {
                        return resultSet.getDouble("score");
                    } else {
                        System.out.println("Student " + name + " does not have permission to access the score.");
                        return -1;
                    }
                } else {
                    System.out.println("Student " + name + " not found.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RemoteException("Database retrieval error", e);
        }
    }

    // Method to close connection when server shuts down
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}