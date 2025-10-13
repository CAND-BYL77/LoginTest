package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageIdLength() {
        Message m = new Message(0, "+27718693002", "Hi Mike");
        assertTrue(m.checkMessageID(), "Message ID should be 10 digits long");
    }

    @Test
    public void testRecipientValidation() {
        Message good = new Message(0, "+27718693002", "Hi Mike");
        assertTrue(good.checkRecipientCell(), "Valid recipient should pass");

        Message bad = new Message(1, "077123", "Hello");
        assertFalse(bad.checkRecipientCell(), "Invalid recipient should fail");
    }

    @Test
    public void testMessageHashCreation() {
        Message m = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash = m.createMessageHash();
        assertNotNull(hash, "Hash should not be null");
        assertTrue(hash.contains(":"), "Hash should contain ':' separator");
        assertTrue(hash.matches("^[0-9]{2}:0:.*"), "Hash format should match XX:messageNumber:firstWordLastWord");
    }

    @Test
    public void testSendStoreDisregardResponses() {
        Message sendMsg = new Message(0, "+27718693002", "Short message");
        assertEquals("Message successfully sent.", sendMsg.sendOrStoreMessage("send"));

        Message storeMsg = new Message(1, "+27718693002", "Short message 2");
        assertEquals("Message successfully stored.", storeMsg.sendOrStoreMessage("store"));

        Message disregardMsg = new Message(2, "+27718693002", "Short message 3");
        assertEquals("Press 0 to delete message.", disregardMsg.sendOrStoreMessage("disregard"));
    }

    @Test
    public void testMessageLengthValidation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) sb.append("x"); // 260 characters
        Message big = new Message(0, "+27718693002", sb.toString());
        String res = big.sendOrStoreMessage("send");
        assertTrue(res.startsWith("Message exceeds 250 characters"), "Messages >250 chars should fail to send");
    }

    @Test
    public void testPrintMessageAndJson() {
        Message m = new Message(3, "+27718693002", "Hello World");
        String printed = m.printMessage();
        assertTrue(printed.contains("Message ID"));
        assertTrue(printed.contains("Recipient"));
        assertTrue(printed.contains("Hello World"));

        String json = m.toJson();
        assertTrue(json.contains("\"messageID\""));
        assertTrue(json.contains("\"messageText\": \"Hello World\""));
    }

    @Test
    public void testTotalMessagesSent() {
        int initial = Message.returnTotalMessages();
        Message m1 = new Message(0, "+27718693002", "Message 1");
        Message m2 = new Message(1, "+27718693002", "Message 2");

        m1.sendOrStoreMessage("send");
        m2.sendOrStoreMessage("send");

        int totalSent = Message.returnTotalMessages();
        assertEquals(initial + 2, totalSent, "Total messages sent should increase by 2");
    }
}
