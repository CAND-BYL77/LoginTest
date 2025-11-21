package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void resetStatics() {
        // Reset indexes
        Message.sentIndex = 0;
        Message.storedIndex = 0;
        Message.disregardedIndex = 0;

        // Reset arrays
        for (int i = 0; i < 100; i++) {
            Message.sentMessages[i] = null;
            Message.storedMessages[i] = null;
            Message.disregardedMessages[i] = null;
            Message.messageHashes[i] = null;
            Message.messageIDsArray[i] = null;
        }
    }


    // Sent Messages Correctly Populated

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        Message m1 = new Message(1, "0838884567", "Did you get the cake?");
        Message m2 = new Message(2, "0838884567", "It is dinner time!");

        m1.sendOrStoreMessage(1);
        m2.sendOrStoreMessage(1);

        String result = Message.showSentMessages();

        assertTrue(result.contains("Did you get the cake?"));
        assertTrue(result.contains("It is dinner time!"));
    }


    // Hash Creation

    @Test
    public void testMessageHashCreation() {
        Message m = new Message(5, "0838884567", "Hi Mike can you join us");

        String hash = m.generateHash();

        assertNotNull(hash);
        assertTrue(hash.contains(":"));
        assertTrue(hash.matches("^[0-9]{2}:5:.*"));
    }


    //  Send / Store / Disregard

    @Test
    public void testSendStoreDisregard() {
        Message sendMsg = new Message(1, "0838884567", "Short message");
        assertEquals("Message sent.", sendMsg.sendOrStoreMessage(1));

        Message storeMsg = new Message(2, "0838884567", "Short message 2");
        assertEquals("Message stored.", storeMsg.sendOrStoreMessage(2));

        Message disregardMsg = new Message(3, "0838884567", "Short message 3");
        assertEquals("Message disregarded.", disregardMsg.sendOrStoreMessage(3));
    }


    // Limit check

    @Test
    public void testMessageLengthLimit() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("x");

        Message m = new Message(1, "0838884567", sb.toString());
        String result = m.sendOrStoreMessage(1);

        assertTrue(result.startsWith("Message exceeds 250 characters"));
    }


    // Sent + Full Report

    @Test
    public void testShowSentAndFullReport() {
        Message m1 = new Message(1, "0838884567", "Hello One");
        Message m2 = new Message(2, "0838884567", "Hello Two");

        m1.sendOrStoreMessage(1);
        m2.sendOrStoreMessage(1);

        String sent = Message.showSentMessages();
        assertTrue(sent.contains("Hello One"));
        assertTrue(sent.contains("Hello Two"));

        String report = Message.fullReport();
        assertTrue(report.contains("Message ID"));
        assertTrue(report.contains("Hash"));
    }


    // Longest Message

    @Test
    public void testShowLongestMessage() {
        Message a = new Message(1, "0838884567", "Short");
        Message b = new Message(2, "0838884567", "This is the longest message here");

        a.sendOrStoreMessage(1);
        b.sendOrStoreMessage(1);

        String result = Message.showLongestMessage();
        assertTrue(result.contains("longest message"));
    }


    // Search by ID & Recipient

    @Test
    public void testSearchByIDAndRecipient() {
        Message m = new Message(7, "0838884567", "Find me by id");
        m.sendOrStoreMessage(1);

        String id = m.getMessageID();
        String found = Message.searchByID(id);

        assertTrue(found.contains("Find me by id"));

        Message a = new Message(8, "0838884567", "A");
        Message b = new Message(9, "0812345678", "B");

        a.sendOrStoreMessage(1);
        b.sendOrStoreMessage(1);

        String results = Message.searchByRecipient("0838884567");
        assertTrue(results.contains("A"));
        assertFalse(results.contains("B"));
    }


    // Delete by Hash

    @Test
    public void testDeleteByHash() {
        Message m = new Message(10, "0838884567", "Delete me");
        m.sendOrStoreMessage(1);

        String hash = Message.messageHashes[0];
        assertNotNull(hash);

        String result = Message.deleteByHash(hash);

        assertEquals("Message deleted.", result);
        assertEquals("DELETED", Message.sentMessages[0]);
        assertNull(Message.messageHashes[0]);
        assertNull(Message.messageIDsArray[0]);
    }
}
