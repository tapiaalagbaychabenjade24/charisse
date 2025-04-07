package org.example.Databases;

import java.sql.*;
import static java.sql.DriverManager.getConnection;

public class portfolioDatabase {

    private static final String url = "jdbc:sqlite:database.db";

    // Constructor for initializing the database connection
    public portfolioDatabase() throws SQLException {
    }

    // Create Portfolio table with a balance and timestamp
    public static void createPortfolioBalanceTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Portfolio ("
                + "balanceId INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "availableBalance FLOAT, "
                + "lastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"; // Add a timestamp column

        try (Connection connection = getConnection(url);
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            System.out.println("Table Portfolio successfully created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUSDT(float usdtAmount) {
        String insertSQL = "INSERT INTO Portfolio (availableBalance) VALUES (?)"; // Insert a new record for each transaction

        try (Connection connection = getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setFloat(1, usdtAmount);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("USDT added successfully!");
            } else {
                System.out.println("Failed to add USDT.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static float getTotalBalance() {
        String selectSQL = "SELECT SUM(availableBalance) AS totalBalance FROM Portfolio"; // Sum of all balances

        try (Connection connection = getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            if (resultSet.next()) {
                return resultSet.getFloat("totalBalance");
            } else {
                return 0.0f;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    // Insert a new record in the Portfolio
    public static void createNewPortfolio(float initialBalance) {
        String insertSQL = "INSERT INTO Portfolio (availableBalance) VALUES (?)";

        try (Connection connection = getConnection(url);
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setFloat(1, initialBalance);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New portfolio created successfully!");
            } else {
                System.out.println("Failed to create portfolio.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve the last update timestamp
    public static Timestamp getLastUpdated() {
        String selectSQL = "SELECT lastUpdated FROM Portfolio WHERE balanceId = 1"; // assuming one record

        try (Connection connection = getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            if (resultSet.next()) {
                return resultSet.getTimestamp("lastUpdated");
            } else {
                return null; // Return null if no record is found
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null if there's an error
        }
    }
}
