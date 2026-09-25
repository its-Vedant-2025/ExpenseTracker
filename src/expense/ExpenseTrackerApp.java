package expense;

import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        manager.setExpenses(FileHandler.loadExpenses());

        while (true) {
            System.out.println("\n=== EXPENSE TRACKER ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expense");
            System.out.println("3. Set Monthly Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Save and Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (choice) {
                case 1:
                    addExpense(sc, manager);
                    break;
                case 2:
                    manager.viewAllExpenses();
                    break;
                case 3:
                    setBudget(sc, manager);
                    break;
                case 4:
                    deleteExpense(sc, manager);
                    break;
                case 5:
                    FileHandler.saveExpenses(manager.getExpenses());
                    System.out.println("Thanks for using our application");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Choose between 1 and 5.");
            }
        }
    }

    private static void addExpense(Scanner sc, ExpenseManager manager) {
        try {
            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            System.out.print("Enter category (Food/Travel/Shopping/Bills/Entertainment/Other): ");
            String category = sc.nextLine().trim();

            System.out.print("Enter date (DD-MM-YYYY): ");
            String date = sc.nextLine().trim();
            if (date.isEmpty()) {
                System.out.println("Date cannot be empty.");
                return;
            }

            System.out.print("Enter description: ");
            String description = sc.nextLine().trim();

            manager.addExpense(amount, category, date, description);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format entered.");
        }
    }

    private static void deleteExpense(Scanner sc, ExpenseManager manager) {
        if (manager.getExpenses().isEmpty()) {
            System.out.println("No expenses to delete.");
            return;
        }

        System.out.println("\nCurrent Expenses:");
        for (Expense e : manager.getExpenses()) {
            System.out.println("ID " + e.getId() + ": Rs. " + e.getAmount() + " (" + e.getCategory() + " - " + e.getDescription() + ")");
        }

        System.out.print("\nEnter ID to delete: ");
        try {
            int id = Integer.parseInt(sc.nextLine().trim());
            manager.deleteExpense(id);
        } catch (Exception e) {
            System.out.println("Invalid ID entered.");
        }
    }

    private static void setBudget(Scanner sc, ExpenseManager manager) {
        if (manager.getMonthlyBudget() > 0) {
            System.out.println("Current Monthly Budget: Rs. " + manager.getMonthlyBudget());
        } else {
            System.out.println("Current Monthly Budget: Not Set");
        }

        System.out.print("Enter monthly budget (Rs): ");
        try {
            double budget = Double.parseDouble(sc.nextLine().trim());
            if (budget > 0) {
                manager.setMonthlyBudget(budget);
                System.out.println("Monthly budget set to Rs. " + budget);
            } else {
                System.out.println("Budget must be positive.");
            }
        } catch (Exception e) {
            System.out.println("Invalid budget amount.");
        }
    }
}