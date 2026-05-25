/**
 * ATM.java
 * Core ATM class that drives the entire ATM Simulation System.
 * Handles the user interface, PIN verification, and all ATM operations.
 * Uses a menu-driven loop for interaction.
 *
 * Author: Internship Submission
 * Project: ATM Simulation System
 */

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ATM {

    // -------------------------------------------------------
    // Constants
    // -------------------------------------------------------
    private static final int MAX_PIN_ATTEMPTS = 3; // Max wrong PIN tries before lockout
    private static final String DIVIDER =
            "  ============================================================";

    // -------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------
    private Account account;    // The account loaded into this ATM
    private Scanner scanner;    // Scanner for reading user input

    // -------------------------------------------------------
    // Constructor
    // -------------------------------------------------------

    /**
     * Constructs an ATM linked to a given bank account.
     *
     * @param account The Account object this ATM session will operate on
     */
    public ATM(Account account) {
        this.account = account;
        this.scanner = new Scanner(System.in);
    }

    // -------------------------------------------------------
    // Session Start: Welcome Screen & PIN Login
    // -------------------------------------------------------

    /**
     * Entry point for an ATM session.
     * Displays a welcome screen, then handles PIN verification.
     * After successful login, launches the main menu.
     */
    public void startSession() {
        displayWelcomeScreen();
        simulateLoading("  Initializing ATM System", 3);

        boolean authenticated = handleLogin();

        if (authenticated) {
            showMainMenu();
        } else {
            displayLockoutMessage();
        }

        displayGoodbyeMessage();
        scanner.close();
    }

    /**
     * Prints the welcome banner to the console.
     */
    private void displayWelcomeScreen() {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  ||                                                        ||");
        System.out.println("  ||          WELCOME TO JAVA NATIONAL BANK                ||");
        System.out.println("  ||               ATM SIMULATION SYSTEM                   ||");
        System.out.println("  ||                                                        ||");
        System.out.println(DIVIDER);
        System.out.println();
    }

    /**
     * Simulates a loading animation with dots.
     *
     * @param message  The text to display while loading
     * @param dotCount Number of dots to print
     */
    private void simulateLoading(String message, int dotCount) {
        System.out.print(message);
        for (int i = 0; i < dotCount; i++) {
            try {
                Thread.sleep(400); // Pause to simulate loading
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print(".");
        }
        System.out.println(" Ready!\n");
    }

    // -------------------------------------------------------
    // PIN Login Logic
    // -------------------------------------------------------

    /**
     * Manages the PIN entry and verification loop.
     * Allows up to MAX_PIN_ATTEMPTS tries before locking out.
     *
     * @return true if PIN was verified successfully, false if locked out
     */
    private boolean handleLogin() {
        int attemptsLeft = MAX_PIN_ATTEMPTS;

        System.out.println(DIVIDER);
        System.out.println("  Please insert your card and enter your PIN.");
        System.out.println(DIVIDER);

        while (attemptsLeft > 0) {
            System.out.print("\n  Enter your 4-digit PIN: ");
            int enteredPin = readIntInput();

            if (account.verifyPin(enteredPin)) {
                System.out.println("\n  PIN verified successfully!");
                simulateLoading("  Loading your account", 3);
                System.out.println("  Welcome back, " + account.getAccountHolder() + "!");
                System.out.println("  Account No: " + account.getAccountNumber());
                return true;
            } else {
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    System.out.println("  [ERROR] Incorrect PIN. You have "
                            + attemptsLeft + " attempt(s) remaining.");
                } else {
                    System.out.println("  [ERROR] Incorrect PIN.");
                }
            }
        }

        return false; // All attempts exhausted
    }

    // -------------------------------------------------------
    // Main Menu
    // -------------------------------------------------------

    /**
     * Displays and drives the main ATM menu.
     * Uses a do-while loop to keep the session running until the user exits.
     */
    private void showMainMenu() {
        int choice = -1;

        do {
            printMainMenu();
            choice = readIntInput();
            System.out.println();

            // Route the user's choice to the appropriate action
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    showTransactionHistory();
                    break;
                case 5:
                    System.out.println("  Exiting session. Thank you!\n");
                    break;
                default:
                    System.out.println("  [!] Invalid option. Please choose between 1 and 5.\n");
            }

        } while (choice != 5);
    }

    /**
     * Prints the formatted main menu options.
     */
    private void printMainMenu() {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  ||                   MAIN MENU                           ||");
        System.out.println(DIVIDER);
        System.out.println("  ||  [1]  Check Balance                                   ||");
        System.out.println("  ||  [2]  Deposit Money                                   ||");
        System.out.println("  ||  [3]  Withdraw Money                                  ||");
        System.out.println("  ||  [4]  Transaction History                             ||");
        System.out.println("  ||  [5]  Exit                                            ||");
        System.out.println(DIVIDER);
        System.out.print("  Enter your choice: ");
    }

    // -------------------------------------------------------
    // ATM Operations
    // -------------------------------------------------------

    /**
     * Displays the current balance of the account.
     * Also logs the balance check in transaction history.
     */
    private void checkBalance() {
        System.out.println(DIVIDER);
        System.out.println("  BALANCE INQUIRY");
        System.out.println(DIVIDER);
        System.out.printf("  Available Balance:  Rs. %.2f%n", account.getBalance());
        System.out.println(DIVIDER);
        account.logBalanceCheck();
        pause();
    }

    /**
     * Handles money deposits.
     * Reads the deposit amount, validates it, updates the account,
     * and shows the new balance.
     */
    private void depositMoney() {
        System.out.println(DIVIDER);
        System.out.println("  CASH DEPOSIT");
        System.out.println(DIVIDER);
        System.out.print("  Enter deposit amount (Rs.): ");

        double amount = readDoubleInput();

        if (amount <= 0) {
            System.out.println("  [ERROR] Deposit amount must be greater than zero.");
        } else {
            boolean success = account.deposit(amount);
            if (success) {
                System.out.printf("%n  Deposit of Rs. %.2f was successful!%n", amount);
                System.out.printf("  Updated Balance:  Rs. %.2f%n", account.getBalance());
                System.out.println(DIVIDER);
            } else {
                System.out.println("  [ERROR] Deposit failed. Please try again.");
            }
        }

        pause();
    }

    /**
     * Handles money withdrawals.
     * Reads the withdrawal amount, validates it, ensures sufficient funds,
     * and updates the account.
     */
    private void withdrawMoney() {
        System.out.println(DIVIDER);
        System.out.println("  CASH WITHDRAWAL");
        System.out.println(DIVIDER);
        System.out.printf("  Current Balance:  Rs. %.2f%n", account.getBalance());
        System.out.print("  Enter withdrawal amount (Rs.): ");

        double amount = readDoubleInput();

        if (amount <= 0) {
            System.out.println("  [ERROR] Withdrawal amount must be greater than zero.");
        } else if (amount > account.getBalance()) {
            System.out.println("  [ERROR] Insufficient funds!");
            System.out.printf("  Available Balance:  Rs. %.2f%n", account.getBalance());
        } else {
            boolean success = account.withdraw(amount);
            if (success) {
                System.out.printf("%n  Withdrawal of Rs. %.2f was successful!%n", amount);
                System.out.printf("  Remaining Balance:  Rs. %.2f%n", account.getBalance());
                System.out.println(DIVIDER);
                System.out.println("  Please collect your cash.");
            } else {
                System.out.println("  [ERROR] Withdrawal failed. Please try again.");
            }
        }

        pause();
    }

    /**
     * Displays all past transactions for this session.
     * Shows a message if no transactions have been recorded yet.
     */
    private void showTransactionHistory() {
        System.out.println(DIVIDER);
        System.out.println("  TRANSACTION HISTORY");
        System.out.println(DIVIDER);

        List<Transaction> history = account.getTransactionHistory();

        if (history.isEmpty()) {
            System.out.println("  No transactions have been made in this session.");
        } else {
            System.out.println("  #   Type                 | Amount          | Balance After");
            System.out.println("  -----------------------------------------------------------------");
            for (int i = 0; i < history.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + history.get(i).toString());
            }
            System.out.println("  -----------------------------------------------------------------");
            System.out.printf("  Total Transactions: %d%n", history.size());
        }

        System.out.println(DIVIDER);
        pause();
    }

    // -------------------------------------------------------
    // End-of-Session Messages
    // -------------------------------------------------------

    /**
     * Displayed when the user exceeds maximum PIN attempts.
     */
    private void displayLockoutMessage() {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  CARD BLOCKED — Too many incorrect PIN attempts.");
        System.out.println("  Please contact your bank for assistance.");
        System.out.println(DIVIDER);
    }

    /**
     * Generic goodbye message at end of session.
     */
    private void displayGoodbyeMessage() {
        System.out.println();
        System.out.println(DIVIDER);
        System.out.println("  ||         Thank you for using Java National Bank ATM     ||");
        System.out.println("  ||               Please take your card.                  ||");
        System.out.println(DIVIDER);
        System.out.println();
    }

    // -------------------------------------------------------
    // Input Helper Methods
    // -------------------------------------------------------

    /**
     * Safely reads an integer from the user.
     * Handles non-integer inputs gracefully by returning -1.
     *
     * @return Integer entered by the user, or -1 on invalid input
     */
    private int readIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the invalid input from buffer
            System.out.println("  [!] Invalid input. Please enter a number.");
            return -1;
        }
    }

    /**
     * Safely reads a double (decimal number) from the user.
     * Handles non-numeric inputs gracefully by returning -1.
     *
     * @return Double entered by the user, or -1 on invalid input
     */
    private double readDoubleInput() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("  [!] Invalid input. Please enter a valid amount.");
            return -1;
        }
    }

    /**
     * Pauses and waits for the user to press Enter before continuing.
     * Keeps the console readable by not rushing to the next menu.
     */
    private void pause() {
        System.out.print("\n  Press Enter to continue...");
        scanner.nextLine();
    }
}