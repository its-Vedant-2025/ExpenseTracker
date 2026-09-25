package expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private List<Expense> expenses;
    private int nextId;
    private double monthlyBudget;

    public ExpenseManager() {
        expenses = new ArrayList<>();
        nextId = 1;
        monthlyBudget = 0.0;
    }

    public void setMonthlyBudget(double budget) {
        this.monthlyBudget = budget;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void addExpense(double amount, String category, String date, String description) {
        Expense e = new Expense(nextId, amount, category, date, description);
        expenses.add(e);
        nextId++;
        System.out.println("Expense added!");

        // Budget summary if a budget has been set
        if (monthlyBudget > 0) {
            String monthYear = getMonthYear(date);
            double totalForMonth = getMonthTotal(monthYear);
            double remaining = monthlyBudget - totalForMonth;

            System.out.println("\nTotal spent for " + monthYear + ": Rs. " + totalForMonth + " / " + monthlyBudget);
            System.out.println("Remaining balance: Rs. " + remaining);

            if (remaining < 0) {
                System.out.println("[!] Alert: You have exceeded your budget by Rs. " + Math.abs(remaining) + "!");
            }
        }
    }

    // Extracts MM-YYYY from DD-MM-YYYY format
    private String getMonthYear(String date) {
        if (date.contains("-") && date.length() >= 7) {
            return date.substring(date.indexOf("-") + 1);
        }
        return date;
    }

    public double getMonthTotal(String monthYear) {
        double total = 0;
        for (Expense e : expenses) {
            if (e.getDate().endsWith(monthYear)) {
                total += e.getAmount();
            }
        }
        return total;
    }

    public void viewAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        for (Expense e : expenses) {
            System.out.println(e);
        }
        System.out.println("--------------------");
        System.out.println("Total Expenses: Rs. " + getTotalExpenses());
    }

    public void searchByCategory(String category) {
        boolean found = false;
        System.out.println("\n--- Expenses for " + category + " ---");
        for (Expense e : expenses) {
            if (e.getCategory().equalsIgnoreCase(category)) {
                System.out.println(e);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No records found in this category.");
        }
    }

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

    public void categorySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\n--- Category Summary ---");
        String[] categories = {"Food", "Travel", "Shopping", "Bills", "Entertainment", "Other"};
        for (String cat : categories) {
            double total = 0;
            for (Expense e : expenses) {
                if (e.getCategory().equalsIgnoreCase(cat)) {
                    total += e.getAmount();
                }
            }
            if (total > 0) {
                System.out.println(cat + ": Rs. " + total);
            }
        }
    }

    public double getTotalExpenses() {
        double sum = 0;
        for (Expense e : expenses) {
            sum += e.getAmount();
        }
        return sum;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> loaded) {
        this.expenses = loaded;
        int maxId = 0;
        for (Expense e : expenses) {
            if (e.getId() > maxId) {
                maxId = e.getId();
            }
        }
        this.nextId = maxId + 1;
    }
}