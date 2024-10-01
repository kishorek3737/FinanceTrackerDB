package main.java.model;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private double amount;
    private String description;
    private String type; // "Income" or "Expense"
    private LocalDate date;

    public Transaction(double amount, String description, String type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = LocalDate.now();
    }

    public Transaction(int id, double amount, String description, String type, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
