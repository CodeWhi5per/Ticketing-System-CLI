package CLI.Util;

import CLI.SystemConfig.SystemConfig;

import java.sql.*;

public class DatabaseUtil {

    // Database connection details.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "ticketing_system";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    // Static block to initialize the database connection.
    static {
        try {
            setupDatabase();  // Setup the database and tables if they don't exist.
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database: " + e.getMessage());
        }
    }

    // Save system configuration data to the database.
    public static void saveConfigToDB(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        String insertSQL = "INSERT INTO system_config (total_tickets, ticket_release_rate, customer_retrieval_rate, max_capacity) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Set values to SQL query.
            preparedStatement.setInt(1, totalTickets);
            preparedStatement.setInt(2, ticketReleaseRate);
            preparedStatement.setInt(3, customerRetrievalRate);
            preparedStatement.setInt(4, maxTicketCapacity);

            preparedStatement.executeUpdate();
            System.out.println("✅ Configuration saved to database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load system configurations from the database.
    public static void loadConfigurationsFromDB(SystemConfig systemConfig) {
        String querySQL = "SELECT total_tickets, ticket_release_rate, customer_retrieval_rate, max_capacity " +
                "FROM system_config ORDER BY created_at DESC LIMIT 1";

        try (Connection connection = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                int totalTickets = resultSet.getInt("total_tickets");
                int ticketReleaseRate = resultSet.getInt("ticket_release_rate");
                int customerRetrievalRate = resultSet.getInt("customer_retrieval_rate");
                int maxTicketCapacity = resultSet.getInt("max_capacity");

                // Set these values to the SystemConfig instance.
                systemConfig.setConfigurations(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
                System.out.println();
                System.out.println("✅ Configuration loaded from database successfully!");
            } else {
                System.out.println("No configurations found in the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Setup the database and table if they do not exist.
    private static void setupDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            // Create database if not exists.
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement.executeUpdate(createDatabaseSQL);

            // Use the created database.
            try (Connection dbConnection = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
                 Statement dbStatement = dbConnection.createStatement()) {

                // Create table if not exists.
                String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS system_config (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            total_tickets INT NOT NULL,
                            ticket_release_rate INT NOT NULL,
                            customer_retrieval_rate INT NOT NULL,
                            max_capacity INT NOT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                        );
                        """;
                dbStatement.executeUpdate(createTableSQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error setting up database: " + e.getMessage());
        }
    }
}


