package org.example;
import org.example.ConfigurationManager;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        // Configuration Management
        ConfigurationManager configManager = new ConfigurationManager();

        // Display last configuration if available
        configManager.displayLastConfiguration();

        // Always prompt for configuration
        ConfigurationManager.SystemConfiguration config = promptForConfiguration(configManager);

        // Extract configuration parameters
        int totalTickets = config.getTotalTickets();
        int maxCapacity = config.getMaxCapacity();
        int releaseInterval = config.getReleaseInterval();
        int purchaseInterval = config.getPurchaseInterval();


        // Print out the current configuration
        System.out.println("\n--- New Configuration Details ---");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Max Ticket Capacity: " + maxCapacity);
        System.out.println("Ticket Release Interval: " + releaseInterval + " ms");
        System.out.println("Customer Purchase Interval: " + purchaseInterval + " ms");
        System.out.println("-----------------------------\n");

        // Create ticket pool and threads
        TicketPool ticketPool = new TicketPool(maxCapacity);

        Vendor vendor = new Vendor(ticketPool, releaseInterval, totalTickets);
        Customer customer = new Customer(ticketPool, purchaseInterval);

        Thread vendorThread = new Thread(vendor);
        Thread customerThread = new Thread(customer);

        vendorThread.start();
        customerThread.start();

        System.out.println("Ticketing system started. Press 'Enter' to stop...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        vendorThread.interrupt();
        customerThread.interrupt();

        try {
            vendorThread.join();
            customerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error stopping threads.");
        }

        System.out.println("Ticketing system stopped.");
        System.out.println("Final Report:");
        System.out.println("Remaining tickets: " + ticketPool.getAvailableTickets());
        scanner.close();
    }


    // Method to always prompt for configuration
    private static ConfigurationManager.SystemConfiguration promptForConfiguration(ConfigurationManager configManager) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Real-Time Event Ticketing System ===");

        int totalTickets = getIntInput(scanner, "Enter total tickets (positive integer): ");
        int maxCapacity = getIntInput(scanner, "Enter maximum ticket capacity (positive integer): ");
        int releaseInterval = getIntInput(scanner, "Enter ticket release interval in milliseconds (positive integer): ");
        int purchaseInterval = getIntInput(scanner, "Enter customer purchase interval in milliseconds (positive integer): ");

        // Create and save configuration
        ConfigurationManager.SystemConfiguration config =
                new ConfigurationManager.SystemConfiguration(
                        totalTickets,
                        maxCapacity,
                        releaseInterval,
                        purchaseInterval
                );

        // Save the configuration
        configManager.saveConfiguration(config);

        return config;
    }

    // Existing method to get validated integer input
    private static int getIntInput(Scanner scanner, String prompt) {
        int value = -1;
        while (value <= 0) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                if (value <= 0) {
                    System.out.println("Error: Please enter a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a positive integer.");
                scanner.next();
            }
        }
        return value;
    }
}