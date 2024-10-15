package com.pluralsight;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate _date, LocalTime _time, String _description, String _vendor, double _amount) {
        this.date = _date;
        this.time = _time;
        this.description = _description;
        this.vendor = _vendor;
        this.amount = _amount;
    }
    // Empty constructor
    public Transaction() {
        this.date = null; // Need help with what to put here
        this.time = null; //
        this.description = "";
        this.vendor = "";
        this.amount = 0;
    }

    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getDescription() {
        return description;
    }
    public String getVendor() {
        return vendor;
    }
    public double getAmount() {
        return amount;
    }

    public String toCSV() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%s|%s|%s|%.2f", date.format(dateFormatter), time.format(timeFormatter), description, vendor,amount);

    }
// It allows the method to be called directly on the class without needing to create an instance of Transaction first.
    public static Transaction fromCSV(String csvLine) {
        String[] partsToSplit = csvLine.split("\\|");
        LocalDate date = LocalDate.parse(partsToSplit[0]);
        LocalTime time = LocalTime.parse(partsToSplit[1]);
        String description = partsToSplit[2];
        String vendor = partsToSplit[3];
        double amount = Double.parseDouble(partsToSplit[4]);
        return new Transaction(date, time, description,vendor,amount);
    }

    @Override
    public String toString() {
        return String.format("%s %s | %s | %s | %.2f", date, time, description, vendor, amount);
    }

}
