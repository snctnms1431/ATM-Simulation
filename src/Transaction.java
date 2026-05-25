/**
 * Transaction.java
 * Represents a single ATM transaction record.
 * Stores the type of transaction, the amount, and the resulting balance.
 *
 * Author: Internship Submission
 * Project: ATM Simulation System
 */

public class Transaction {

    // -------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------
    private String type;       // e.g., "DEPOSIT", "WITHDRAWAL", "BALANCE CHECK"
    private double amount;     // Amount involved in the transaction
    private double balanceAfter; // Account balance right after this transaction

    // -------------------------------------------------------
    // Constructor
    // -------------------------------------------------------

    /**
     * Creates a new Transaction record.
     *
     * @param type         The type of transaction performed
     * @param amount       The monetary amount of the transaction
     * @param balanceAfter The account balance following this transaction
     */
    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    // -------------------------------------------------------
    // Getters (Encapsulation)
    // -------------------------------------------------------

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    // -------------------------------------------------------
    // Display Method
    // -------------------------------------------------------

    /**
     * Returns a neatly formatted string representation of this transaction.
     * Used when printing the transaction history to the console.
     */
    @Override
    public String toString() {
        if (type.equals("BALANCE CHECK")) {
            return String.format("  %-20s | Amount: %-12s | Balance: Rs. %.2f",
                    type, "---", balanceAfter);
        }
        return String.format("  %-20s | Amount: Rs. %-8.2f | Balance: Rs. %.2f",
                type, amount, balanceAfter);
    }
}