package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private final String csvFile = "transactions.csv"; // File where we store the transactions

    // This method reads all transactions from the file
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                LocalDate date = LocalDate.parse(parts[0].trim());
                LocalTime time = LocalTime.parse(parts[1].trim());
                String description = parts[2].trim();
                String vendor = parts[3].trim();
                double amount = Double.parseDouble(parts[4].trim());

                transactions.add(new Transaction(date, time, description, vendor, amount));
            }
        } catch (IOException e) {
            System.out.println("Could not read the file: " + e.getMessage());
        }
        return transactions;
    }

    // This method adds a new transaction to the file
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



