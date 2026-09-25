package expense;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistence layer managing plain-text CSV reading and writing operations.
 */
public class FileHandler {
    // Relative path to persistent CSV file
    private static final String FILE_NAME = "expenses.txt";

    /**
     * Serializes memory objects to disk line by line using comma delimiters.
     */
    public static void saveExpenses(List<Expense> expenses) {
        // Try-with-resources statement ensures automatic resource closing
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                // Record schema: ID,Amount,Category,ISO-Date,Description
                writer.write(e.getId() + "," + 
                             e.getAmount() + "," + 
                             e.getCategory() + "," + 
                             e.getDate() + "," + 
                             e.getDescription());
                writer.newLine();
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Deserializes CSV lines into Expense objects on boot.
     */
    public static List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        File file = new File(FILE_NAME);

        // Gracefully return empty list if application is running for the first time
        if (!file.exists()) {
            return expenses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Limit split to 5 parts so descriptions containing commas are preserved
                String[] parts = line.split(",", 5);
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    double amount = Double.parseDouble(parts[1].trim());
                    String category = parts[2].trim();
                    LocalDate date = LocalDate.parse(parts[3].trim()); // Parses standard ISO-8601 (yyyy-MM-dd)
                    String description = parts[4].trim();

                    expenses.add(new Expense(id, amount, category, date, description));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return expenses;
    }
}