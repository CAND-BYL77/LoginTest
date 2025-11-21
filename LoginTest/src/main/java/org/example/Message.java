package org.example;

import java.util.Random;

public class Message {

    private int messageNumber;
    private String recipientNumber;
    private String message;
    private String messageID;
    private String messageHash;

    // Arrays to store messages
    public static String[] sentMessages = new String[100];
    public static String[] storedMessages = new String[100];
    public static String[] disregardedMessages = new String[100];
    public static String[] messageHashes = new String[100];
    public static String[] messageIDsArray = new String[100];

    public static int sentIndex = 0;
    public static int storedIndex = 0;
    public static int disregardedIndex = 0;

    // Constructor
    public Message(int messageNumber, String recipientNumber, String message) {
        this.messageNumber = messageNumber;
        this.recipientNumber = recipientNumber;
        this.message = message;
        this.messageID = generateNumericMessageID();
        this.messageHash = generateHash();
        messageIDsArray[sentIndex] = this.messageID; // ensure ID is tracked
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipientNumber; }
    public String getMessage() { return message; }

    public void createMessageHash() { this.messageHash = generateHash(); }

    // Generate random 10-digit numeric ID
    private String generateNumericMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // Generate hash based on first & last word + first two digits of ID
    public String generateHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();
        String firstTwoDigits = messageID.substring(0, 2);
        return firstTwoDigits + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // Send/Store/Disregard
    public String sendOrStoreMessage(int option) {
        switch (option) {
            case 1 -> { // SEND
                if (message.length() > 250)
                    return "Message exceeds 250 characters by " + (message.length() - 250) + ".";

                sentMessages[sentIndex] = "ID: " + messageID + " | To: " + recipientNumber + " | " + message;
                messageHashes[sentIndex] = messageHash;
                messageIDsArray[sentIndex] = messageID;
                sentIndex++;
                return "Message sent.";
            }
            case 2 -> { // STORE
                storedMessages[storedIndex] = "To: " + recipientNumber + " | " + message;
                storedIndex++;
                return "Message stored.";
            }
            case 3 -> { // DISREGARD
                disregardedMessages[disregardedIndex] = "To: " + recipientNumber + " | " + message;
                disregardedIndex++;
                return "Message disregarded.";
            }
            default -> { return "Invalid option."; }
        }
    }

    // Return counts
    public static int returnTotalMessages() { return sentIndex; }
    public static int returnTotalStoredMessages() { return storedIndex; }

    // Show sent messages
    public static String showSentMessages() {
        if (sentIndex == 0) return "No sent messages.";
        StringBuilder sb = new StringBuilder("Sent Messages:\n\n");
        for (int i = 0; i < sentIndex; i++) sb.append(sentMessages[i]).append("\n");
        return sb.toString();
    }

    // Show stored messages
    public static String showStoredMessages() {
        if (storedIndex == 0) return "No stored messages.";
        StringBuilder sb = new StringBuilder("Stored Messages:\n\n");
        for (int i = 0; i < storedIndex; i++) sb.append(storedMessages[i]).append("\n");
        return sb.toString();
    }

    // Longest sent message
    public static String showLongestMessage() {
        if (sentIndex == 0) return "No messages.";
        int longest = 0;
        int index = 0;
        for (int i = 0; i < sentIndex; i++) {
            if (sentMessages[i].length() > longest) {
                longest = sentMessages[i].length();
                index = i;
            }
        }
        return "Longest Sent Message:\n" + sentMessages[index];
    }

    // Search by Message ID
    public static String searchByID(String id) {
        for (int i = 0; i < sentIndex; i++) {
            if (messageIDsArray[i] != null && messageIDsArray[i].equals(id))
                return "Message Found:\n" + sentMessages[i];
        }
        return "Message ID not found.";
    }

    // Search by recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages sent to " + recipient + ":\n\n");
        boolean found = false;
        for (int i = 0; i < sentIndex; i++) {
            if (sentMessages[i].contains(recipient)) {
                sb.append(sentMessages[i]).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No messages to this recipient.";
    }

    // Delete by hash
    public static String deleteByHash(String hash) {
        for (int i = 0; i < sentIndex; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(hash)) {
                sentMessages[i] = "DELETED";
                messageHashes[i] = null;
                messageIDsArray[i] = null;
                return "Message deleted.";
            }
        }
        return "Hash not found.";
    }

    // Full report
    public static String fullReport() {
        if (sentIndex == 0) return "No data available.";
        StringBuilder sb = new StringBuilder("FULL REPORT:\n\n");
        for (int i = 0; i < sentIndex; i++) {
            sb.append("Message ID: ").append(messageIDsArray[i]).append("\n");
            sb.append("Hash: ").append(messageHashes[i]).append("\n");
            sb.append("Sent Data: ").append(sentMessages[i]).append("\n\n");
        }
        return sb.toString();
    }
}
