
package main.java.service;

import main.java.dao.TransactionDAO;
import main.java.model.Transaction;

import java.util.List;
import java.util.Scanner;

public class FinanceService {
    private TransactionDAO transactionDAO;
    private double balance;
    private double budget;

    public FinanceService() {
        this.transactionDAO = new TransactionDAO();
        this.balance = transactionDAO.getBalance();
        this.budget = transactionDAO.getBudget();
    }

    public void viewBalance() {
        System.out.println("=========================================");
        System.out.println("       Current Balance");
        System.out.println("=========================================");
        System.out.printf("Your current balance is: $%.2f%n", balance);
        System.out.println("=========================================");
    }

    public void addIncome(Scanner scanner) {
        System.out.println("=========================================");
        System.out.println("       Add Income");
        System.out.println("=========================================");
        System.out.print("Enter the income amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter a description: ");
        String description = scanner.nextLine();

        Transaction income = new Transaction(amount, description, "Income");
        transactionDAO.addTransaction(income);
        balance += amount;
        transactionDAO.updateBalance(balance);

        System.out.println("Income added successfully!");
        System.out.printf("Your new balance is: $%.2f%n", balance);
        System.out.println("=========================================");
    }

    public void addExpense(Scanner scanner) {
        System.out.println("=========================================");
        System.out.println("       Add Expense");
        System.out.println("=========================================");
        System.out.print("Enter the expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter a description: ");
        String description = scanner.nextLine();

        if (amount > balance) {
            System.out.println("Insufficient funds!");
            return;
        }

        Transaction expense = new Transaction(amount, description, "Expense");
        transactionDAO.addTransaction(expense);
        balance -= amount;
        transactionDAO.updateBalance(balance);

        System.out.println("Expense added successfully!");
        System.out.printf("Your new balance is: $%.2f%n", balance);
        System.out.println("=========================================");
    }

    public void viewTransactionHistory() {
        List<Transaction> transactions = transactionDAO.getAllTransactions();
        System.out.println("=========================================");
        System.out.println("       Transaction History");
        System.out.println("=========================================");
        System.out.println("ID   |  Type    |  Amount  |  Description  |  Date");
        System.out.println("-----------------------------------------");

        for (Transaction transaction : transactions) {
            System.out.printf("%-4d | %-8s | $%-7.2f | %-12s | %s%n",
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getDate());
        }

        System.out.println("=========================================");
    }

    public void setBudget(Scanner scanner) {
        System.out.println("=========================================");
        System.out.println("       Set Monthly Budget");
        System.out.println("=========================================");
        System.out.print("Enter the budget amount: ");
        budget = scanner.nextDouble();
        scanner.nextLine();

        transactionDAO.updateBudget(budget);

        System.out.println("Budget set successfully for this month!");
        System.out.println("=========================================");
    }

    public void viewBudgetReport() {
        double totalExpenses = transactionDAO.getTotalExpenses();
        double remainingBudget = budget - totalExpenses;

        System.out.println("=========================================");
        System.out.println("       Budget Report");
        System.out.println("=========================================");
        System.out.printf("Total Budget: $%.2f%n", budget);
        System.out.printf("Total Expenses: $%.2f%n", totalExpenses);
        System.out.printf("Remaining Budget: $%.2f%n", remainingBudget);
        System.out.println("=========================================");
    }
}
