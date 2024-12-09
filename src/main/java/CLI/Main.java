package CLI;


import CLI.Core.TicketPool;
import CLI.Core.Vendor;
import CLI.Core.Customer;

import CLI.SystemConfig.SystemConfig;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        // Scanner object to read user inputs.
        Scanner scanner = new Scanner(System.in);
        SystemConfig systemConfig = new SystemConfig();   // Instance of SystemConfig to manage system settings.
        boolean systemRunning = false;

        // Display system header.

        System.out.println();
        System.out.println("     ┌──────────────────────────────────────┐");
        System.out.println("     │   REAL-TIME EVENT TICKETING SYSTEM   │");
        System.out.println("     └──────────────────────────────────────┘");
        System.out.println();

        // Main loop to manage the user input for system commands.
        while (true) {

            // Ask user for command: START or STOP
            System.out.println("Enter command: START or STOP.");
            System.out.print("> ");
            String command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "START":

                    systemRunning = true;
                    System.out.println();
                    System.out.println("⚙\uFE0F System Started Successfully......");
                    System.out.println();

                    // Menu for system operations once started.
                    while (systemRunning) {
                        System.out.println("Choose an option to operate the system:");
                        System.out.println();
                        System.out.println("1. \uD83D\uDEE0\uFE0F Enter The System Configurations");
                        System.out.println("2. \uD83D\uDFE2 Start The Simulation");
                        System.out.println("3. \uD83D\uDD34 Stop The Simulation");
                        System.out.println();

                        int choice;

                        // Validate menu input
                        while (true) {
                            System.out.print("Enter your choice: ");
                            if (scanner.hasNextInt()) {
                                choice = scanner.nextInt();
                                scanner.nextLine(); // Consume the newline
                                if (choice >= 1 && choice <= 3) {
                                    break;
                                } else {
                                    System.out.println("❌ Invalid choice! Please enter a number between 1 and 3.");
                                }
                            } else {
                                System.out.println("❌ Invalid input! Please enter a valid number.");
                                scanner.nextLine(); // Consume invalid input
                            }
                        }

                        // Handle the user's choice based on their input.
                        switch (choice) {
                            case 1:
                                // Configure the system and save settings to the database.
                                systemConfig.systemConfiguration();
                                System.out.println();
                                systemConfig.saveConfigurationsToDB();
                                System.out.println();
                                break;

                            case 2:
                                // Start the simulation.
                                systemConfig.loadConfigurationsFromDB();
                                System.out.println();

                                int vendorCount;
                                int customerCount;

                                // Validate vendor count input.
                                while (true) {
                                    System.out.print("Enter Vendor Count: ");
                                    if (scanner.hasNextInt()) {
                                        vendorCount = scanner.nextInt();
                                        if (vendorCount > 0) {
                                            break;
                                        } else {
                                            System.out.println("❌ Vendor count must be greater than 0.");
                                        }
                                    } else {
                                        System.out.println("❌ Invalid input! Please enter a valid number.");
                                        scanner.nextLine(); // Consume invalid input.
                                    }
                                }

                                // Validate customer count input.
                                while (true) {
                                    System.out.print("Enter Customer Count: ");
                                    if (scanner.hasNextInt()) {
                                        customerCount = scanner.nextInt();
                                        if (customerCount > 0) {
                                            break;
                                        } else {
                                            System.out.println("❌ Customer count must be greater than 0.");
                                        }
                                    } else {
                                        System.out.println("❌ Invalid input! Please enter a valid number.");
                                        scanner.nextLine(); // Consume invalid input.
                                    }
                                }
                                scanner.nextLine(); // Consume newline.

                                System.out.println();
                                System.out.println("⚙\uFE0F Simulation started successfully.");
                                System.out.println();

                                // Create threads for vendors and customers.
                                List<Thread> threads = getThreads(systemConfig, vendorCount, customerCount);

                                // Wait for all threads to complete
                                for (Thread thread : threads) {
                                    try {
                                        thread.join();  // Waits for each thread to finish.
                                    } catch (InterruptedException e) {
                                        System.out.println("⚠\uFE0F A thread was interrupted: " + e.getMessage());
                                    }
                                }

                                System.out.println();
                                System.out.println("✅ Simulation completed. All tickets are processed.");
                                System.out.println();
                                break;

                            case 3:
                                // Stop the system and exit the loop.
                                System.out.println();
                                System.out.println("⚙\uFE0F System stopped successfully......");
                                System.out.println();
                                systemRunning = false;
                                break;

                            default:
                                System.out.println("⚠\uFE0F Unexpected error occurred."); // This shouldn't happen due to validation
                        }
                    }
                    break;

                case "STOP":

                    // Exits the program if the command is STOP.
                    System.out.println();
                    System.out.println("\uD83D\uDED1 Exiting the program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("❌ Invalid command! Please enter START or STOP");
            }
        }
    }

    // Creates and starts vendor and customer threads.
    private static List<Thread> getThreads(SystemConfig systemConfig, int vendorCount, int customerCount) {
        TicketPool ticketPool = new TicketPool(systemConfig.getTotalTickets(), systemConfig.getMaxTicketCapacity());


        List<Thread> threads = new ArrayList<>();

        // Create and start vendor threads.
        for (int i = 0; i < vendorCount; i++) {
            Vendor vendor = new Vendor(i + 1, ticketPool, systemConfig.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor);
            threads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer .
        for (int i = 0; i < customerCount; i++) {
            Customer customer = new Customer(i + 1, ticketPool, systemConfig.getCustomerRetrievalRate());
            Thread customerThread = new Thread(customer);
            threads.add(customerThread);
            customerThread.start();
        }
        return threads;
    }
}


