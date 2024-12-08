package CLI;


import CLI.Core.TicketPool;
import CLI.Core.Vendor;
import CLI.Core.Customer;

import CLI.SystemConfig.SystemConfig;


import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SystemConfig systemConfig = new SystemConfig();

        while (true) {
            System.out.println("Real-Time Event Ticketing System");
            System.out.println();
            System.out.println("Choose an option to START the System:");
            System.out.println("01. Enter The System Configurations");
            System.out.println("02. Start The Simulation");
            System.out.println("03. Exit");

            int choice = 0;

            // Validate menu input
            while (true) {
                System.out.print("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    if (choice >= 1 && choice <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid choice! Please enter a number between 1 and 3.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.nextLine(); // Consume invalid input
                }
            }

            switch (choice) {
                case 1:
                    // Configure the system and save settings to the database
                    systemConfig.systemConfiguration();
                    System.out.println();
                    systemConfig.saveConfigurationsToDB();
                    System.out.println();
                    break;

                case 2:
                    // Start the simulation
                    systemConfig.loadConfigurationsFromDB();
                    System.out.println();

                    int vendorCount = 0;
                    int customerCount = 0;

                    // Validate vendor count input
                    while (true) {
                        System.out.print("Enter Vendor Count: ");
                        if (scanner.hasNextInt()) {
                            vendorCount = scanner.nextInt();
                            if (vendorCount > 0) {
                                break;
                            } else {
                                System.out.println("Vendor count must be greater than 0.");
                            }
                        } else {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.nextLine(); // Consume invalid input
                        }
                    }

                    // Validate customer count input
                    while (true) {
                        System.out.print("Enter Customer Count: ");
                        if (scanner.hasNextInt()) {
                            customerCount = scanner.nextInt();
                            if (customerCount > 0) {
                                break;
                            } else {
                                System.out.println("Customer count must be greater than 0.");
                            }
                        } else {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.nextLine(); // Consume invalid input
                        }
                    }
                    scanner.nextLine(); // Consume newline

                    TicketPool ticketPool = new TicketPool(systemConfig.getTotalTickets(), systemConfig.getMaxTicketCapacity());

                    // Create and start vendor threads
                    for (int i = 0; i < vendorCount; i++) {
                        Vendor vendor = new Vendor(i + 1, ticketPool, systemConfig.getTicketReleaseRate());
                        Thread vendorThread = new Thread(vendor);
                        vendorThread.start();
                    }

                    // Create and start customer threads
                    for (int i = 0; i < customerCount; i++) {
                        Customer customer = new Customer(i + 1, ticketPool, systemConfig.getCustomerRetrievalRate());
                        Thread customerThread = new Thread(customer);
                        customerThread.start();
                    }
                    break;

                case 3:
                    // Exit the program
                    System.out.println("System Stopped.");
                    scanner.close();
                    return; // Use return to exit the method

                default:
                    System.out.println("Unexpected error occurred."); // This shouldn't happen due to validation
            }
        }
    }
}


