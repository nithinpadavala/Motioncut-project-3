import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Expense {
    String date;
    String category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }
}

class User {
    String username;
    String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

class ExpenseTracker {
    private ArrayList<Expense> expenses = new ArrayList<>();
    private User currentUser;

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void listExpenses() {
        for (Expense expense : expenses) {
            System.out.println("Date: " + expense.date + ", Category: " + expense.category + ", Amount: " + expense.amount);
        }
    }

    public void saveExpensesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(currentUser.username + "_expenses.ser"))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving expenses to file.");
        }
    }

    public void loadExpensesFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentUser.username + "_expenses.ser"))) {
            expenses = (ArrayList<Expense>) ois.readObject();
            System.out.println("Expenses loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No expenses found or error loading expenses from file.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker expenseTracker = new ExpenseTracker();

        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User currentUser = new User(username, password);
        expenseTracker.setCurrentUser(currentUser);
        expenseTracker.loadExpensesFromFile();

        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Save Expenses to File");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter date (MM/DD/YYYY): ");
                    String date = scanner.next();
                    System.out.print("Enter category: ");
                    String category = scanner.next();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();

                    Expense newExpense = new Expense(date, category, amount);
                    expenseTracker.addExpense(newExpense);
                    System.out.println("Expense added successfully!");
                    break;

                case 2:
                    System.out.println("List of Expenses:");
                    expenseTracker.listExpenses();
                    break;

                case 3:
                    expenseTracker.saveExpensesToFile();
                    break;

                case 4:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    expenseTracker.saveExpensesToFile();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
