package CLI.SystemConfig;


import CLI.Core.TicketPool;
import CLI.Util.DatabaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class SystemConfig {

    // Logger to log systemConfig actions in CLI and the ticketing_system.log file.
    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    // Scanner object to read user inputs.
    private final Scanner scanner = new Scanner(System.in);

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // Method to configure system settings.
    public void systemConfiguration() {

        System.out.println();
        totalTickets = validatePositiveInt("Enter Total Tickets: ");
        ticketReleaseRate = validatePositiveInt("Enter Ticket Release Rate (Seconds): ");
        customerRetrievalRate = validatePositiveInt("Enter Customer Retrieval Rate (Seconds): ");
        maxTicketCapacity = validatePositiveInt("Enter Max Ticket Capacity: ");
        System.out.println();
        logConfigData(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity); // calling logConfigData method to log config data to CLI and the ticketing_system.log file.
    }

    // Method to validate that the input is a positive integer.
    private int validatePositiveInt(String prompt) {
        int value = 0;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("❌ Input must be a positive number. Please try again.");
                }
            } else {
                System.out.println("❌ Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    // Save configurations to database.
    public void saveConfigurationsToDB() {
        DatabaseUtil.saveConfigToDB(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    // Load configurations from the database.
    public void loadConfigurationsFromDB() {
        DatabaseUtil.loadConfigurationsFromDB(this);
    }

    // Set system configuration values.
    public void setConfigurations(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;

        System.out.println();
        logConfigData(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    // Log the configuration data.
    public void logConfigData(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity){

        logger.info("Total Tickets: {}", totalTickets);
        logger.info("Ticket Release Rate: {}", ticketReleaseRate);
        logger.info("Customer Retrieval Rate: {}", customerRetrievalRate);
        logger.info("Max Ticket Capacity: {}", maxTicketCapacity);
    }

    // Getter methods for system configuration.
    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}

