package org.example;

import javax.swing.JOptionPane;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessagesSent = 0;

    // Constructor
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    //Generate 10-digit random message ID
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    //Check if message ID is valid
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    //Check recipient cellphone format
    public boolean checkRecipientCell() {
        // Must start with +27 and be followed by 9 digits (total 12 characters)
        return recipient.matches("^\\+27\\d{9}$");
    }

    //Create Message Hash
    public String createMessageHash() {
        try {
            String[] words = messageText.trim().split(" ");
            String firstWord = words[0];
            String lastWord = words[words.length - 1];
            String idPart = messageID.substring(0, 2);
            return (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        } catch (Exception e) {
            return "HASH_ERROR";
        }
    }

    //Send or Store Message
    public String sendOrStoreMessage(String option) {
        // Check if message exceeds 250 characters
        if (messageText.length() > 250) {
            return "Message exceeds 250 characters and cannot be sent.";
        }

        switch (option.toLowerCase()) {
            case "send":
                totalMessagesSent++;
                return "Message successfully sent.";
            case "store":
                return "Message successfully stored.";
            case "disregard":
                return "Press 0 to delete message.";
            default:
                return "Invalid option. Please select send, store, or disregard.";
        }
    }
    //Convert message details to JSON format (optional, for Part 3)
    public String toJson() {
        return "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"messageText\": \"" + messageText + "\"\n" +
                "}";
    }

    //Display message details
    public String printMessage() {
        return "Message ID: " + messageID +
                "\nMessage Hash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText;
    }

    //Return total sent messages
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
