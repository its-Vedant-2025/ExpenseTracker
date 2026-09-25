package expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class managing in-memory business logic and operations on expenses.
 */
public class ExpenseManager {
    // Dynamic container holding all active expense items
    private List<Expense> expenses;
    
    // Auto-incrementing counter tracking transaction IDs
    private int nextId;

    // Monthly spending budget limit (0.0 means not set)
    private double monthlyBudget;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        this.nextId = 1;
        this.monthlyBudget = 0.0;
    }

    /**
     * Sets or updates the monthly budget threshold.
     */
    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    /**
     * Returns the current monthly budget.
     */
    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    /**
     * Calculates total expenses recorded in the current calendar month and year.
     */
    public double getCurrentMonthTotal() {
        LocalDate now = LocalDate.now();
        double sum = 0.0;
        for (Expense e : expenses) {
            if (e.getDate().getMonth() == now.getMonth() && e.getDate().getYear() == now.getYear()) {
                sum += e.getAmount();
            }
        }
        return sum;
    }

    /**
     * Evaluates spending against the monthly budget limit.
     * Returns a warning message string if threshold is hit, or null if within budget.
     */
    public String checkBudgetAlert() {
        if (monthlyBudget <= 0.0) {
            return null; // Budget not configured
        }

        double currentTotal = getCurrentMonthTotal();
        double percentage = (currentTotal / monthlyBudget) * 100.0;

        if (percentage >= 100.0) {
            return String.format("[!] ALERT: You have exceeded your monthly budget of Rs. %.2f! (Spent: Rs. %.2f - %.1f%%)",
                    monthlyBudget, currentTotal, percentage);
        } else if (percentage >= 80.0) {
            return String.format("[!] WARNING: You have reached %.1f%% of your monthly budget of Rs. %.2f! (Spent: Rs. %.2f)",
                    percentage, monthlyBudget, currentTotal);
        }
        return null;
    }

    /**
     * Creates a new Expense object, appends it to the collection, and increments ID.
     */
    public void addExpense(double amount, String category, LocalDate date, String description) {
        Expense expense = new Expense(nextId, amount, category, date, description);
        expenses.add(expense);
        nextId++;
        System.out.println("Expense added successfully!");
    }

    /**
     * Iterates through and prints all stored expenses.
     */
    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("\n====== All Expenses ======");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    /**
     * Filters and outputs expenses matching a specific category case-insensitively.
     */
    public void searchByCategory(String category) {
        boolean found = false;
        System.out.println("\n====== Expenses in Category: " + category + " ======");
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                System.out.println(e);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No expenses found in this category.");
        }
    }

    /**
     * Removes an expense matching the target ID by index.
     */
    public void deleteExpense(int id) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == id) {
                expenses.remove(i);
                System.out.println("Expense deleted successfully!");
                return;
            }
        }
        System.out.println("Expense with ID " + id + " not found.");
    }

    /**
     * Aggregates total expenditure grouped across predefined standard categories.
     */
    public void categorySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\n====== Category-wise Summary ======");
        String[] categories = {"Food", "Travel", "Shopping", "Bills", "Entertainment", "Others"};
        
        for (String cat : categories) {
            double total = 0;
            for (Expense e : expenses) {
                if (e.getCategory().equalsIgnoreCase(cat)) {
                    total += e.getAmount();
                }
            }
            if (total > 0) {
                System.out.println(cat + ": Rs. " + String.format("%.2f", total));
            }
        }
    }

    /**
     * Calculates cumulative expenditure across all recorded transactions.
     */
    public double getTotalExpenses() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    // Accessor for file serialization
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * Sets in-memory records and recalculates nextId to avoid primary key collisions.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        if (!expenses.isEmpty()) {
            // Pick highest existing ID to avoid duplicate primary keys
            int maxId = 0;
            for (Expense e : expenses) {
                if (e.getId() > maxId) {
                    maxId = e.getId();
                }
            }
            this.nextId = maxId + 1;
        }
    }
}