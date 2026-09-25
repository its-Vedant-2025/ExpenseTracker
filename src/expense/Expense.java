package expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing a single financial expense record.
 */
public class Expense {
    // Unique identifier for each expense transaction
    private int id;
    
    // Monetary value of the transaction
    private double amount;
    
    // Grouping classification (e.g., Food, Travel, Bills)
    private String category;
    
    // Date on which the transaction occurred
    private LocalDate date;
    
    // Contextual description or remarks
    private String description;

    // Parameterized constructor to initialize a complete expense record
    public Expense(int id, double amount, String category, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    // --- Setters ---
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Formats expense details into a readable console line.
     * Formats LocalDate to dd-MM-yyyy pattern and standardizes currency to Rs.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "ID: " + id + 
               " | Amount: Rs. " + String.format("%.2f", amount) + 
               " | Category: " + category + 
               " | Date: " + date.format(formatter) + 
               " | Description: " + description;
    }
}