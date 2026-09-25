package expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class managing in-memory business logic and operations on expenses.
 */
public class ExpenseManager {
    private List<Expense> expenses;
    private int nextId;

    // Single global monthly budget limit
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
     * Returns the configured monthly budget.
     */
    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    /**
     * Calculates total expenses recorded in the target month and year.
     */
    public double getMonthTotal(LocalDate targetDate) {
        double sum = 0.0;
        for (Expense e : expenses) {
            if (e.getDate().getMonth() == targetDate.getMonth() && e.getDate().getYear() == targetDate.getYear()) {
                sum += e.getAmount();
            }
        }
        return sum;
    }

    /**
     * Calculates total expenses recorded in the current system month and year.
     */
    public double getCurrentMonthTotal() {
        return getMonthTotal(LocalDate.now());
    }

    /**
     * Generates a status message with spent amount and remaining balance for the expense's month.
     */
    public String getBudgetStatus(LocalDate targetDate) {
        if (monthlyBudget <= 0.0) {
            return null; // Budget not set yet
        }

        double totalSpent = getMonthTotal(targetDate);
        String monthName = targetDate.getMonth().name() + " " + targetDate.getYear();

        if (totalSpent > monthlyBudget) {
            double overspent = totalSpent - monthlyBudget;
            return String.format("[!] ALERT: You have exceeded your budget for %s!\n" +
                                 "    Total Spent: Rs. %.2f | Budget: Rs. %.2f\n" +
                                 "    Overspent by: Rs. %.2f",
                                 monthName, totalSpent, monthlyBudget, overspent);
        } else {
            double remaining = monthlyBudget - totalSpent;
            return String.format("[i] Budget Update for %s:\n" +
                                 "    Total Spent: Rs. %.2f of Rs. %.2f\n" +
                                 "    Remaining  : Rs. %.2f",
                                 monthName, totalSpent, monthlyBudget, remaining);
        }
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

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        if (!expenses.isEmpty()) {
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