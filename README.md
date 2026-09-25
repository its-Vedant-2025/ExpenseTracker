# Personal Expense Tracker

A lightweight, console-based Personal Expense Tracker built in core Java. It allows users to record daily expenses, monitor a monthly spending budget with real-time balance updates, and persist transaction records locally.

## Features

- **Add Expense**: Log transactions with amount, category, date (`DD-MM-YYYY`), and description.
- **View Expense**: Display all logged expenses along with cumulative total expenditure.
- **Set Monthly Expense**: Configure a monthly spending budget. When logging expenses, it calculates the total for that specific month and displays the remaining balance (or alerts if overspent).
- **Delete Expense**: View existing records and remove entries by ID.
- **Data Persistence**: Automatically loads and saves all expense entries to a local `expenses.txt` file.

## Project Structure

```text
ExpenseTracker/
├── src/
│   └── expense/
│       ├── Expense.java           # Model class representing an expense record
│       ├── ExpenseManager.java    # Business logic for tracking, summing, and budgets
│       ├── FileHandler.java       # Handles reading and writing to expenses.txt
│       └── ExpenseTrackerApp.java # CLI menu and user interactions
├── expenses.txt                   # Local storage file (generated on save)
└── README.md
```

## How to Run

1. Clone the repository:
```bash
git clone https://github.com/its-Vedant-2025/ExpenseTracker.git
cd ExpenseTracker
```

2. Compile the source code:
```bash
javac -d src src/expense/*.java
```

3. Run the application:
```bash
java -cp src expense.ExpenseTrackerApp
```

## Menu Options

```text
=== EXPENSE TRACKER ===
1. Add Expense
2. View Expense
3. Set Monthly Expense
4. Delete Expense
5. Save and Exit
```
