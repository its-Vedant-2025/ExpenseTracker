package expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Entry point for the CLI Expense Tracker application.
 */
public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        // Restore persisted state from local disk on application boot
        manager.setExpenses(FileHandler.loadExpenses());

        // Core command processing loop
        while (true) {
            System.out.println("\n====== Personal Expense Tracker ======");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Search by Category");
            System.out.println("4. Delete Expense");
            System.out.println("5. Category-wise Summary");
            System.out.println("6. Total Expenses");
            System.out.println("7. Set / View Monthly Budget");
            System.out.println("8. Save & Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid numeric input! Please enter a valid menu number.");
                sc.nextLine(); // Discard faulty input
                continue;
            }
            sc.nextLine(); // Clear newline buffer

            switch (choice) {
                case 1:
                    addExpense(sc, manager);
                    break;
                case 2:
                    manager.viewAllExpenses();
                    break;
                case 3:
                    System.out.print("Enter category (Food/Travel/Shopping/Bills/Entertainment/Others): ");
                    String category = sc.nextLine().trim();
                    manager.searchByCategory(category);
                    break;
                case 4:
                    System.out.print("Enter Expense ID to delete: ");
                    try {
                        int id = sc.nextInt();
                        sc.nextLine();
                        manager.deleteExpense(id);
                    } catch (Exception e) {
                        System.out.println("Invalid ID! ID must be an integer.");
                        sc.nextLine();
                    }
                    break;
                case 5:
                    manager.categorySummary();
                    break;
                case 6:
                    System.out.println("Total Expenses: Rs. " + String.format("%.2f", manager.getTotalExpenses()));
                    break;
                case 7:
                    handleBudget(sc, manager);
                    break;
                case 8:
                    FileHandler.saveExpenses(manager.getExpenses());
                    System.out.println("Thank you for using Expense Tracker!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 8.");
            }
        }
    }

    /**
     * Prompts user input and handles validation before passing records to ExpenseManager.
     */
    private static void addExpense(Scanner sc, ExpenseManager manager) {
        try {
            System.out.print("Enter amount: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // Clear newline buffer

            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return;
            }

            System.out.print("Enter category (Food/Travel/Shopping/Bills/Entertainment/Others): ");
            String category = sc.nextLine().trim();

            System.out.print("Enter date (dd-MM-yyyy) or press Enter for today: ");
            String dateInput = sc.nextLine().trim();
            LocalDate date;

            if (dateInput.isEmpty()) {
                date = LocalDate.now();
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDate.parse(dateInput, formatter);
            }

            System.out.print("Enter description: ");
            String description = sc.nextLine().trim();

            manager.addExpense(amount, category, date, description);

            // Print spent vs remaining info for this expense's month
            String budgetMessage = manager.getBudgetStatus(date);
            if (budgetMessage != null) {
                System.out.println("\n" + budgetMessage);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Please use dd-MM-yyyy.");
        } catch (Exception e) {
            System.out.println("Invalid input! Please check your values and try again.");
            sc.nextLine();
        }
    }

    /**
     * View and configure monthly budget limit.
     */
    private static void handleBudget(Scanner sc, ExpenseManager manager) {
        LocalDate today = LocalDate.now();
        String currentMonthName = today.getMonth().name() + " " + today.getYear();

        System.out.println("\n====== Monthly Budget Management ======");
        if (manager.getMonthlyBudget() > 0) {
            System.out.printf("Configured Monthly Budget : Rs. %.2f\n", manager.getMonthlyBudget());
            System.out.printf("Current Month Spending (%s): Rs. %.2f\n", currentMonthName, manager.getCurrentMonthTotal());
            String status = manager.getBudgetStatus(today);
            if (status != null) {
                System.out.println(status);
            }
        } else {
            System.out.println("Configured Monthly Budget : Not Set");
        }

        System.out.print("\nEnter new monthly budget limit (or 0 to keep current): Rs. ");
        try {
            double newBudget = Double.parseDouble(sc.nextLine().trim());
            if (newBudget > 0) {
                manager.setMonthlyBudget(newBudget);
                System.out.printf("Monthly budget updated to Rs. %.2f successfully!\n", newBudget);
            } else {
                System.out.println("Budget unchanged.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Returning to menu.");
        }
    }
}