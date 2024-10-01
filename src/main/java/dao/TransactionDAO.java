
//dao data object access the files which accessing the data using methods like CRUD in the Database is metioned as DAO
//LIke TransactionDAO ETC.,
package main.java.dao;

import main.java.model.Transaction;
import main.java.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO() {
        this.connection = DatabaseUtil.getConnection();
    }

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (amount, description, type, date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, transaction.getAmount());
            stmt.setString(2, transaction.getDescription());
            stmt.setString(3, transaction.getType());
            stmt.setDate(4, Date.valueOf(transaction.getDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getDate("date").toLocalDate()
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public double getBalance() {
        String sql = "SELECT balance FROM user_account WHERE id = 1";
        double balance = 0.0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }

    public void updateBalance(double balance) {
        String sql = "UPDATE user_account SET balance = ? WHERE id = 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, balance);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBudget() {
        String sql = "SELECT budget FROM user_account WHERE id = 1";
        double budget = 0.0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                budget = rs.getDouble("budget");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return budget;
    }

    public void updateBudget(double budget) {
        String sql = "UPDATE user_account SET budget = ? WHERE id = 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, budget);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTotalExpenses() {
        String sql = "SELECT SUM(amount) AS total_expenses FROM transactions WHERE type = 'Expense'";
        double totalExpenses = 0.0;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                totalExpenses = rs.getDouble("total_expenses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalExpenses;
    }
}

