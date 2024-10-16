package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private final String csvFile = "transactions.csv"; // File where we store the transactions

    // Reads all transactions from the file
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                // Check if the first part contains both date and time
                if (parts.length != 4) {
                    System.out.println("Invalid line format: " + line);
                    continue; // Skip invalid lines
                    // Thank you Osmig
                }
                // Combine and trim the date-time part
                String dateTimePart = parts[0].trim();

                // Parse the date and time
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimePart, dateTimeFormatter);
                    LocalDate date = dateTime.toLocalDate();
                    LocalTime time = dateTime.toLocalTime();

                    // Get description, vendor, and amount
                    String description = parts[1].trim();
                    String vendor = parts[2].trim();
                    double amount = Double.parseDouble(parts[3].trim());

                    // Add the transaction to the list
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                } catch (Exception e) {
                    System.out.println("Error parsing date or time in line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read the file: " + e.getMessage());
        }

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        }

        return transactions;
    }


    // Adds a new transaction to the file
    public void addTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
            writer.write(transaction.toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Could not write to the file: " + e.getMessage());
        }
    }

    // Get all the transactions
    public List<Transaction> getAllTransactions() {
        return loadTransactions();
    }

    // Get only deposits (positive amounts)
    public List<Transaction> getDeposits() {
        List<Transaction> deposits = new ArrayList<>();
        for (Transaction transaction : loadTransactions()) {
            if (transaction.getAmount() > 0) {
                deposits.add(transaction);
            }
        }
        return deposits;
    }

    // Get only payments (negative amounts)
    public List<Transaction> getPayments() {
        List<Transaction> payments = new ArrayList<>();
        for (Transaction transaction : loadTransactions()) {
            if (transaction.getAmount() < 0) {
                payments.add(transaction);
            }
        }
        return payments;
    }
}


