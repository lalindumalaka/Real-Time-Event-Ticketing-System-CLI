package org.example;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigurationManager {
    private static final String CONFIG_FILE_PATH = "ticket_system_config.json";
    private Gson gson;

    public static class SystemConfiguration {
        private int totalTickets;
        private int maxCapacity;
        private int releaseInterval;
        private int purchaseInterval;

        public SystemConfiguration(int totalTickets, int maxCapacity,
                                   int releaseInterval, int purchaseInterval) {
            this.totalTickets = totalTickets;
            this.maxCapacity = maxCapacity;
            this.releaseInterval = releaseInterval;
            this.purchaseInterval = purchaseInterval;
        }

        public int getTotalTickets() { return totalTickets; }
        public int getMaxCapacity() { return maxCapacity; }
        public int getReleaseInterval() { return releaseInterval; }
        public int getPurchaseInterval() { return purchaseInterval; }
    }

    public ConfigurationManager() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public boolean saveConfiguration(SystemConfiguration config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            gson.toJson(config, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
            return false;
        }
    }

    public SystemConfiguration loadLastConfiguration() {
        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            return gson.fromJson(reader, SystemConfiguration.class);
        } catch (IOException e) {
            System.out.println("No previous configuration found. Using default.");
            return null;
        }
    }

    public void displayLastConfiguration() {
        SystemConfiguration lastConfig = loadLastConfiguration();
        if (lastConfig != null) {
            System.out.println("\n--- Previous Configuration Details ---");
            System.out.println("Total Tickets: " + lastConfig.getTotalTickets());
            System.out.println("Max Ticket Capacity: " + lastConfig.getMaxCapacity());
            System.out.println("Ticket Release Interval: " + lastConfig.getReleaseInterval() + " ms");
            System.out.println("Customer Purchase Interval: " + lastConfig.getPurchaseInterval() + " ms");
            System.out.println("-------------------------\n");
        } else {
            System.out.println("No previous configuration available.");
        }
    }
}