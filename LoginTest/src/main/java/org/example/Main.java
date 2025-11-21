package org.example;

import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        String usernameAttempt;
        String passwordAttempt;
        String registrationStatus;
        int maxMessages = 0;
        int numMessagesSent = 0;


        // USER REGISTRATION

        JOptionPane.showMessageDialog(null, "***** REGISTER *****", "QuickChat", JOptionPane.INFORMATION_MESSAGE);

        String firstName = JOptionPane.showInputDialog("Enter your first name:");
        String lastName = JOptionPane.showInputDialog("Enter your last name:");
        String username = JOptionPane.showInputDialog("Enter your username (max 5 characters, must contain _ ):");
        String password = JOptionPane.showInputDialog("Enter your password (8+ chars, uppercase, number, special char):");
        String cellphone = JOptionPane.showInputDialog("Enter your South African phone number (+27*********):");

        Login account = new Login();
        registrationStatus = account.registerUser(username, password, cellphone, firstName, lastName);
        JOptionPane.showMessageDialog(null, registrationStatus);
        if (!registrationStatus.equals("User registered successfully.")) return;


        // LOGIN

        JOptionPane.showMessageDialog(null, "***** LOGIN *****", "QuickChat", JOptionPane.INFORMATION_MESSAGE);
        usernameAttempt = JOptionPane.showInputDialog("Enter your username:");
        passwordAttempt = JOptionPane.showInputDialog("Enter your password:");
        boolean loginSuccess = account.loginUser(usernameAttempt, passwordAttempt);
        if (!loginSuccess) { JOptionPane.showMessageDialog(null, "Login failed. Exiting program."); return; }
        JOptionPane.showMessageDialog(null, "Login successful. Welcome back " + firstName + " " + lastName + "!\nWelcome to QuickChat.");


        // ASK MESSAGE LIMIT

        try {
            maxMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
            if (maxMessages <= 0) maxMessages = 1;
        } catch (NumberFormatException e) { maxMessages = 1; }

        final boolean disregardCounts = false;


        // MAIN MENU

        boolean running = true;
        while (running) {
            String menu = """
                QuickChat Menu 
                1) Send Messages
                2) Message Options
                3) Quit
                """;
            int choice;
            try { choice = Integer.parseInt(JOptionPane.showInputDialog(menu + "\nEnter your choice (1-3):")); }
            catch (Exception e) { JOptionPane.showMessageDialog(null, "Invalid input. Enter a number (1-3)."); continue; }

            switch (choice) {
                case 1 -> { // SEND MESSAGES
                    while (numMessagesSent < maxMessages) {

                        String recipient = JOptionPane.showInputDialog("Enter recipient number (+27*********):");
                        if (recipient == null) break;
                        recipient = recipient.trim();
                        if (recipient.isBlank()) { JOptionPane.showMessageDialog(null, "Recipient cannot be empty."); continue; }

                        String text = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                        if (text == null) break;
                        text = text.trim();
                        if (text.isBlank() || text.length() > 250) { JOptionPane.showMessageDialog(null, "Message invalid."); continue; }

                        Message msg = new Message(numMessagesSent + 1, recipient, text);
                        msg.createMessageHash();

                        String[] options = {"Send", "Store", "Disregard"};
                        int action = JOptionPane.showOptionDialog(null, "Choose what to do with this message:",
                                "Send / Store / Disregard", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                        if (action == -1) break;

                        String result = msg.sendOrStoreMessage(action + 1);
                        JOptionPane.showMessageDialog(null, result);

                        boolean wasCounted = result.equals("Message sent.") || result.equals("Message stored.")
                                || (result.equals("Message disregarded.") && disregardCounts);

                        if (wasCounted) numMessagesSent++;

                        if (numMessagesSent >= maxMessages) {
                            JOptionPane.showMessageDialog(null, "You have reached your message limit (" + maxMessages + ").");
                            break;
                        }
                    }
                }

                case 2 -> { // MESSAGE OPTIONS
                    String submenu = """
                        Message Options
                        1) View All Sent Messages
                        2) View Longest Message
                        3) Search by Message ID
                        4) Search by Recipient
                        5) Delete Message by Hash
                        6) Full Report
                        7) View Stored Messages
                        """;
                    int subChoice;
                    try { subChoice = Integer.parseInt(JOptionPane.showInputDialog(submenu)); }
                    catch (Exception e) { JOptionPane.showMessageDialog(null, "Invalid input."); continue; }

                    switch (subChoice) {
                        case 1 -> JOptionPane.showMessageDialog(null, Message.showSentMessages());
                        case 2 -> JOptionPane.showMessageDialog(null, Message.showLongestMessage());
                        case 3 -> {
                            String id = JOptionPane.showInputDialog("Enter Message ID:");
                            JOptionPane.showMessageDialog(null, Message.searchByID(id));
                        }
                        case 4 -> {
                            String rec = JOptionPane.showInputDialog("Enter Recipient:");
                            JOptionPane.showMessageDialog(null, Message.searchByRecipient(rec));
                        }
                        case 5 -> {
                            String hash = JOptionPane.showInputDialog("Enter Hash:");
                            JOptionPane.showMessageDialog(null, Message.deleteByHash(hash));
                        }
                        case 6 -> JOptionPane.showMessageDialog(null, Message.fullReport());
                        case 7 -> JOptionPane.showMessageDialog(null, Message.showStoredMessages());
                        default -> JOptionPane.showMessageDialog(null, "Invalid choice.");
                    }
                }

                case 3 -> { JOptionPane.showMessageDialog(null, "Goodbye " + firstName + "!"); running = false; }
                default -> JOptionPane.showMessageDialog(null, "Invalid selection.");
            }
        }
    }
}
