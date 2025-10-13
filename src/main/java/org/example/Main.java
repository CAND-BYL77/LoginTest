package org.example;

import javax.swing.JOptionPane;

public class Main {
        public static void main(String[] args) {
                Login loginSystem = new Login();

                //REGISTRATION section.
                JOptionPane.showMessageDialog(null, "REGISTRATION");

                // Get all user inputs in main
                String firstName = JOptionPane.showInputDialog("Enter your first name:");
                String lastName = JOptionPane.showInputDialog("Enter your last name:");
                String username = JOptionPane.showInputDialog("Enter username (must contain '_' and be ≤5 characters):");
                String password = JOptionPane.showInputDialog("Enter password (must be ≥8 chars with capital, number, special char):");
                String cellphoneNumber = JOptionPane.showInputDialog("Enter cellphone number (with international code, e.g., +27*********):");

                // Validate each field using Login class methods
                boolean isUsernameValid = loginSystem.checkUserName(username);
                boolean isPasswordValid = loginSystem.checkPasswordComplexity(password);
                boolean isCellphoneValid = loginSystem.checkCellPhoneNumber(cellphoneNumber);

                // Check validations
                if (!isUsernameValid) {
                        JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure it contains an underscore and is no more than five characters.");
                        return;
                }

                if (!isPasswordValid) {
                        JOptionPane.showMessageDialog(null, "Password is not correctly formatted, must contain at least eight characters, a capital letter, a number, and a special character.");
                        return;
                }

                if (!isCellphoneValid) {
                        JOptionPane.showMessageDialog(null, "Cell phone number incorrectly formatted or missing international code.");
                        return;
                }

                // Register user
                String registrationResult = loginSystem.registerUser(username, password, cellphoneNumber, firstName, lastName);
                JOptionPane.showMessageDialog(null, registrationResult);

                //LOGIN section.
                JOptionPane.showMessageDialog(null, "LOGIN");
                String inputUsername = JOptionPane.showInputDialog("Enter username:");
                String inputPassword = JOptionPane.showInputDialog("Enter password:");

                boolean loginSuccess = loginSystem.loginUser(inputUsername, inputPassword);
                String loginStatus = loginSystem.returnLoginStatus(loginSuccess);
                JOptionPane.showMessageDialog(null, loginStatus);

                // If login succeeds, proceed to send a message
                if (loginSuccess) {
                        JOptionPane.showMessageDialog(null, "SEND A MESSAGE");

                        int messageNumber = 1;
                        String recipient = JOptionPane.showInputDialog("Enter recipient cellphone number (+27*********):");
                        String messageText = JOptionPane.showInputDialog("Enter your message:");

                        // Create Message object
                        Message msg = new Message(messageNumber, recipient, messageText);

                        // Check message ID and recipient
                        boolean idValid = msg.checkMessageID();
                        boolean recipientValid = msg.checkRecipientCell();

                        if (!idValid) {
                                JOptionPane.showMessageDialog(null, "Generated Message ID is invalid.");
                        }

                        if (!recipientValid) {
                                JOptionPane.showMessageDialog(null, "Recipient number is invalid. Make sure it starts with +27 and has 12 characters.");
                        }

                        // Ask user whether to send, store, or disregard the message
                        String option = JOptionPane.showInputDialog("Enter option (send, store, disregard):");
                        String sendStatus = msg.sendOrStoreMessage(option);
                        JOptionPane.showMessageDialog(null, sendStatus);

                        // Display message details
                        JOptionPane.showMessageDialog(null, msg.printMessage());

                        // Show total messages sent so far
                        JOptionPane.showMessageDialog(null, "Total messages sent: " + Message.returnTotalMessages());
                }
        }
}
