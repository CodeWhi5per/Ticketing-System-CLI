package CLI.SystemConfig;


import CLI.Core.TicketPool;
import CLI.Util.DatabaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class SystemConfig {

    private static final Logger logger = LogManager.getLogger(TicketPool.class);

    private final Scanner scanner = new Scanner(System.in);

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public void systemConfiguration() {

        System.out.println();
        totalTickets = validatePositiveInt("Enter Total Tickets: ");
        ticketReleaseRate = validatePositiveInt("Enter Ticket Release Rate (Seconds): ");
        customerRetrievalRate = validatePositiveInt("Enter Customer Retrieval Rate (Seconds): ");
        maxTicketCapacity = validatePositiveInt("Enter Max Ticket Capacity: ");
        System.out.println();
        logConfigData(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

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

    public void saveConfigurationsToDB() {
        DatabaseUtil.saveConfigToDB(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    public void loadConfigurationsFromDB() {
        DatabaseUtil.loadConfigurationsFromDB(this);
    }

    public void setConfigurations(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;

        System.out.println();
        logConfigData(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    public void logConfigData(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity){

        logger.info("Total Tickets: {}", totalTickets);
        logger.info("Ticket Release Rate: {}", ticketReleaseRate);
        logger.info("Customer Retrieval Rate: {}", customerRetrievalRate);
        logger.info("Max Ticket Capacity: {}", maxTicketCapacity);
    }

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

