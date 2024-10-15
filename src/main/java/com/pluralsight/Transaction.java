package com.pluralsight;

import java.time.*;

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

}
