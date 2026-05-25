/**
 * Account.java
 * Represents a bank account in the ATM Simulation System.
 * Handles balance management and transaction history.
 * Demonstrates encapsulation using private fields and public methods.
 *
 * Author: Internship Submission
 * Project: ATM Simulation System
 */

import java.util.ArrayList;
import java.util.List;

public class Account {

    // -------------------------------------------------------
    // Instance Variables (Private for Encapsulation)
    // -------------------------------------------------------
    private String accountHolder;   // Name of the account owner
    private String accountNumber;   // Unique account number
    private int pin;                 // 4-digit PIN for authentication
    private double balance;          // Current account balance

    // List to store the history of all transactions
    private List<Transaction> transactionHistory;

    // -------------------------------------------------------
    // Constructor
    // -------------------------------------------------------

    /**
     * Initializes an Account with holder's name, account number,
     * PIN, and an opening balance.
     *
     * @param accountHolder  Name of the account holder
     * @param accountNumber  Account number string
     * @param pin            4-digit numeric PIN
     * @param openingBalance Starting balance for the account
     */
    public Account(String accountHolder, String accountNumber, int pin, double openingBalance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = openingBalance;
        this.transactionHistory = new ArrayList<>();
    }

    // -------------------------------------------------------
    // PIN Verification
    // -------------------------------------------------------

    /**
     * Checks whether the entered PIN matches the stored PIN.
     *
     * @param enteredPin The PIN entered by the user
     * @return true if PIN matches, false otherwise
     */
    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    // -------------------------------------------------------
    // Balance Operations
    // -------------------------------------------------------

    /**
     * Returns the current account balance.
     *
     * @return Current balance as a double
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a specified amount into the account.
     * Validates that the amount is greater than zero.
     *
     * @param amount The amount to deposit
     * @return true if deposit was successful, false if invalid
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false; // Reject zero or negative deposits
        }
        balance += amount;
        // Log this transaction in history
        transactionHistory.add(new Transaction("DEPOSIT", amount, balance));
        return true;
    }

    /**
     * Withdraws a specified amount from the account.
     * Validates that the amount is positive and sufficient funds exist.
     *
     * @param amount The amount to withdraw
     * @return true if withdrawal was successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false; // Reject zero or negative withdrawals
        }
        if (amount > balance) {
            return false; // Reject if insufficient balance
        }
        balance -= amount;
        // Log this transaction in history
        transactionHistory.add(new Transaction("WITHDRAWAL", amount, balance));
        return true;
    }

    /**
     * Records a balance check in the transaction history.
     */
    public void logBalanceCheck() {
        transactionHistory.add(new Transaction("BALANCE CHECK", 0, balance));
    }

    // -------------------------------------------------------
    // Transaction History
    // -------------------------------------------------------

    /**
     * Returns the full list of transactions performed on this account.
     *
     * @return List of Transaction objects
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    // -------------------------------------------------------
    // Getters for Account Details
    // -------------------------------------------------------

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}