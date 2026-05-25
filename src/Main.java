/**
 * Main.java
 * Entry point for the ATM Simulation System.
 * Creates a dummy bank account and launches the ATM session.
 *
 * Author: Internship Submission
 * Project: ATM Simulation System
 *
 * HOW TO COMPILE AND RUN:
 *   javac *.java
 *   java Main
 *
 * Default test credentials:
 *   Account Holder : Rahul Sharma
 *   Account Number : JNB-2024-9081
 *   PIN            : 1234
 *   Opening Balance: Rs. 15,000.00
 */

public class Main {

    public static void main(String[] args) {

        // Create a demo bank account for the simulation
        // In a real system, this would be loaded from a database
        Account demoAccount = new Account(
                "Rahul Sharma",     // Account holder name
                "JNB-2024-9081",    // Account number
                1234,               // 4-digit PIN
                15000.00            // Opening balance in Rs.
        );

        // Create the ATM and link it to the demo account
        ATM atm = new ATM(demoAccount);

        // Start the ATM session — this drives the entire program
        atm.startSession();
    }
}