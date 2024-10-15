package com.pluralsight;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ReportManager {
    private final TransactionManager transactionManager;

    public ReportManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    // Get transactions from the current month
    public List<Transaction> getMonthToDate() {
        List<Transaction> monthToDate = new ArrayList<>();
        LocalDate now = LocalDate.now();
        List<Transaction> allTransactions = transactionManager.getAllTransactions();

        for (Transaction t : allTransactions) {
            if (t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear()) {
                monthToDate.add(t);
            }
        }
        return monthToDate;
    }

    // Get transactions from the previous month
    public List<Transaction> getPreviousMonth() {
        List<Transaction> previousMonth = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);
        List<Transaction> allTransactions = transactionManager.getAllTransactions();

        for (Transaction t : allTransactions) {
            if (t.getDate().getMonth() == lastMonth.getMonth() && t.getDate().getYear() == lastMonth.getYear()) {
                previousMonth.add(t);
            }
        }

        return previousMonth;
    }

    // Search for transactions by vendor
    public List<Transaction> searchByVendor(String vendorName) {
        List<Transaction> result = new ArrayList<>();
        List<Transaction> allTransactions = transactionManager.getAllTransactions();

        for (Transaction t : allTransactions) {
            if (t.getVendor().equalsIgnoreCase(vendorName)) {
                result.add(t);
            }
        }

        return result;
    }
}

