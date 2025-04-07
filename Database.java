package org.example;

import static org.example.Databases.portfolioDatabase.createPortfolioBalanceTable;
import static org.example.Databases.usersDatabase.createUsersTable;

public class Database {

    private static final String url = "jdbc:sqlite:database.db"; // Database URL

    public static void main(String[] args) {
        createPortfolioBalanceTable();
        createUsersTable();
    }
}
