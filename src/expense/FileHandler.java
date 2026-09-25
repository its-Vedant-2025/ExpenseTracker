package expense;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    private static final String FILE_NAME = "expenses.txt";

    public static void saveExpenses(List<Expense> expenses) {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Expense e : expenses) {
                writer.println(e.getId() + "," + e.getAmount() + "," + e.getCategory() + "," + e.getDate() + "," + e.getDescription());
            }
        } catch (Exception e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    public static List<Expense> loadExpenses() {
        List<Expense> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(",", 5);
                    if (parts.length == 5) {
                        int id = Integer.parseInt(parts[0]);
                        double amount = Double.parseDouble(parts[1]);
                        String category = parts[2];
                        String date = parts[3];
                        String description = parts[4];
                        list.add(new Expense(id, amount, category, date, description));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }
}