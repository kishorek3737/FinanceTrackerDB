package main.java.app;

import main.java.service.FinanceService;
import main.java.dao.User;
import main.java.model.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    // Method to establish the database connection
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orclkish", "SCOTT", "kis03");
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            Scanner scanner = new Scanner(System.in);
            boolean isAuthenticated = false;

            // Login/Signup loop
            while (!isAuthenticated) {
                System.out.println("=========================================");
                System.out.println("    Welcome to Personal Finance Tracker");
                System.out.println("=========================================");
                System.out.println("1. Signup");
                System.out.println("2. Login");
                System.out.println("=========================================");
                System.out.print("Enter your choice: ");
                int authChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (authChoice == 1) {
                    // Signup flow
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();

                    // Check if the username already exists
                    if (userDAO.isUsernameTaken(username)) {
                        System.out.println("Username already exists. Please login instead.");
                    } else {
                        User newUser = new User(username, password);
                        boolean isRegistered = userDAO.registerUser(newUser);
                        if (isRegistered) {
                            System.out.println("Signup successful! Please login.");
                        } else {
                            System.out.println("Signup failed. Please try again.");
                        }
                    }
                } else if (authChoice == 2) {
                    // Login flow
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();

                    if (userDAO.loginUser(username, password)) {
                        System.out.println("Login successful!");
                        isAuthenticated = true;
                    } else {
                        // Check if the username exists at all
                        if (!userDAO.isUsernameTaken(username)) {
                            System.out.println("Username not found. Please sign up first.");
                        } else {
                            System.out.println("Login failed. Invalid credentials.");
                        }
                    }
                } else {
                    System.out.println("Invalid choice. Please choose 1 for Signup or 2 for Login.");
                }
            }

            // After successful login, proceed to the main menu
            FinanceService financeService = new FinanceService();
            int choice;

            do {
                System.out.println("=========================================");
                System.out.println("      Personal Finance Tracker");
                System.out.println("=========================================");
                System.out.println("1. View Balance");
                System.out.println("2. Add Income");
                System.out.println("3. Add Expense");
                System.out.println("4. View Transaction History");
                System.out.println("5. Set Budget");
                System.out.println("6. View Budget Report");
                System.out.println("7. Exit");
                System.out.println("=========================================");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        financeService.viewBalance();
                        break;
                    case 2:
                        financeService.addIncome(scanner);
                        break;
                    case 3:
                        financeService.addExpense(scanner);
                        break;
                    case 4:
                        financeService.viewTransactionHistory();
                        break;
                    case 5:
                        financeService.setBudget(scanner);
                        break;
                    case 6:
                        financeService.viewBudgetReport();
                        break;
                    case 7:
                        System.out.println("Thank you for using Personal Finance Tracker! Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } while (choice != 7);

            scanner.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
