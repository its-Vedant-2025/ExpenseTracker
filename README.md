# Personal Expense Tracker

A simple, modular Command Line Interface (CLI) application built using core Java to track daily expenses, categorize spending, and save data locally. 

Built this project as a 2nd-year engineering student to practice core Object-Oriented Programming (OOP) concepts, file handling, and package structuring in Java.

---

## Why I Built This

I wanted to move beyond basic classroom coding problems and build a functional, real-world utility from scratch. This project helped me get hands-on experience with:
- **OOP Principles**: Designing separated domain models (`Expense`) and business controllers (`ExpenseManager`).
- **Data Persistence**: Implementing basic CSV file reading and writing using `BufferedReader` and `BufferedWriter` so data persists between runs.
- **Defensive Input Handling**: Catching runtime exceptions (`DateTimeParseException`, `InputMismatchException`) so the terminal loop doesn't crash on bad inputs.
- **Java Time API**: Working with `LocalDate` and `DateTimeFormatter` for handling transaction dates.

---

## What It Can Do

- **Add New Expenses**: Log transaction amount, category, date (defaults to today if left blank), and a note.
- **View All Logs**: Print all recorded expenses in a clear format.
- **Category Search**: Filter transactions by type (*Food, Travel, Shopping, Bills, Entertainment, Others*).
- **Spending Summary**: Automatically calculates category totals and overall expenditure.
- **Save & Load**: Saves records to `expenses.txt` on exit and automatically reloads them whenever the program starts.

---

## Project Structure

```text
ExpenseTracker/
├── src/
│   └── expense/
│       ├── Expense.java            # Expense model class (getters, setters, formatting)
│       ├── ExpenseManager.java     # Core logic (add, delete, filter, summary)
│       ├── ExpenseTrackerApp.java  # Main CLI menu loop and user prompts
│       └── FileHandler.java        # Handles file I/O (save/load from expenses.txt)
├── .gitignore                      # Ignores .class files and local data
└── README.md
```

---

## How to Run Locally

### Requirements
- Java Development Kit (JDK 8 or above)
- Terminal / VS Code

### Steps

1. **Clone the repo:**
   ```bash
   git clone [https://github.com/its-Vedant-2025/ExpenseTracker.git](https://github.com/its-Vedant-2025/ExpenseTracker.git)
   cd ExpenseTracker
   ```

2. **Compile the files:**
   ```bash
   javac -d src src/expense/*.java
   ```

3. **Run the app:**
   ```bash
   java -cp src expense.ExpenseTrackerApp
   ```

---

## What's Next / Future Improvements

- Add a graphical interface (JavaFX / Swing).
- Export summaries into CSV or PDF format.
- Add monthly expense budgets and alert limits.
