package CLI.SystemConfig;


import CLI.Util.DatabaseUtil;

import java.util.Scanner;

public class SystemConfig {

    private final Scanner scanner = new Scanner(System.in);

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public void systemConfiguration() {

        totalTickets = validatePositiveInt("Enter Total Tickets: ");
        ticketReleaseRate = validatePositiveInt("Enter Ticket Release Rate (Seconds): ");
        customerRetrievalRate = validatePositiveInt("Enter Customer Retrieval Rate (Seconds): ");
        maxTicketCapacity = validatePositiveInt("Enter Max Ticket Capacity: ");
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
                    System.out.println("Input must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
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

