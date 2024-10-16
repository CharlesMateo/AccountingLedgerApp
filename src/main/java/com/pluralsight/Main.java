package com.pluralsight;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    private static final TransactionManager transactionManager = new TransactionManager();
    private static final Reports reportManager = new Reports(transactionManager);
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            // Display menu
            System.out.println("Welcome to the Account Ledger App!");
            System.out.println("---------------------------");
            System.out.println("Home Screen:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("R) Reports");
            System.out.println("X) Exit");
            System.out.println("---------------------------");

            String choice = scan.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    addTransaction(true);
                    break;
                case "P":
                    addTransaction(false);
                    break;
                case "L":
                    showLedger();
                    break;
                case "R":
                    showReports();
                    break;
                case "X":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Adding a transaction (deposit or payment)
    private static void addTransaction(boolean isDeposit) {
        System.out.print("Enter description: ");
        String description = scan.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scan.nextLine();
        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scan.nextLine());

        if (!isDeposit) {
            amount = -Math.abs(amount); // Make sure it's a negative number for payments
        }

        // Transaction with the current date and time
        Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
        transactionManager.addTransaction(transaction);

        System.out.println("Transaction added.");
    }

    // Showing Ledger
    private static void showLedger() {
        System.out.println("Ledger:");
        System.out.println("A) Show All");
        System.out.println("D) Show Deposits");
        System.out.println("P) Show Payments");

        String choice = scan.nextLine().toUpperCase();

        List<Transaction> transactions;
        switch (choice) {
            case "A":
                transactions = transactionManager.getAllTransactions();
                break;
            case "D":
                transactions = transactionManager.getDeposits();
                break;
            case "P":
                transactions = transactionManager.getPayments();
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
    }

    // Reports menu
    private static void showReports() {
        System.out.println("Reports:");
        System.out.println("1) Month to Date");
        System.out.println("2) Previous Month");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");

        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                showTransactions(reportManager.getMonthToDate());
                break;
            case "2":
                showTransactions(reportManager.getPreviousMonth());
                break;
            case "5":
                System.out.print("Enter vendor name: ");
                String vendor = scan.nextLine();
                showTransactions(reportManager.searchByVendor(vendor));
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    // Display transactions
    private static void showTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
    }
}
