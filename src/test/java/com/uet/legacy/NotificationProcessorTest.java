/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationProcessorTest {

    // Fake notification service for testing
    static class FakeNotificationService implements NotificationService {

        String receivedRecipient;
        String receivedMessage;
        int callCount = 0;

        @Override
        public void sendNotification(String recipient, String message) {
            receivedRecipient = recipient;
            receivedMessage = message;
            callCount++;
        }
    }

    @Test
    void sendsEmailNotificationCorrectly() {

        FakeNotificationService email = new FakeNotificationService();
        FakeNotificationService sms = new FakeNotificationService();

        NotificationProcessor processor
                = new NotificationProcessor(email, sms);

        Map<String, Object> data = new HashMap<>();
        data.put("notifyChannel", "EMAIL");

        processor.processNotification(data, "test@example.com", 1500.50);

        assertEquals(1, email.callCount);
        assertEquals(0, sms.callCount);
        assertEquals("test@example.com", email.receivedRecipient);
        assertEquals(
                "Your order of PKR 1500.50 is confirmed!",
                email.receivedMessage
        );
    }

    @Test
    void sendsSmsNotificationCorrectly() {

        FakeNotificationService email = new FakeNotificationService();
        FakeNotificationService sms = new FakeNotificationService();

        NotificationProcessor processor
                = new NotificationProcessor(email, sms);

        Map<String, Object> data = new HashMap<>();
        data.put("notifyChannel", "SMS");
        data.put("phone", "03001234567");

        processor.processNotification(data, "test@example.com", 2000.00);

        assertEquals(0, email.callCount);
        assertEquals(1, sms.callCount);
        assertEquals("03001234567", sms.receivedRecipient);
        assertEquals(
                "Order confirmed! Paid: PKR 2000.00",
                sms.receivedMessage
        );
    }

    @Test
    void usesEmailAsDefaultNotificationChannel() {

        FakeNotificationService email = new FakeNotificationService();
        FakeNotificationService sms = new FakeNotificationService();

        NotificationProcessor processor
                = new NotificationProcessor(email, sms);

        Map<String, Object> data = new HashMap<>();

        processor.processNotification(data, "user@example.com", 500.00);

        assertEquals(1, email.callCount);
        assertEquals(0, sms.callCount);
        assertEquals("user@example.com", email.receivedRecipient);
    }

    @Test
    void usesDefaultPhoneNumberForSms() {

        FakeNotificationService email = new FakeNotificationService();
        FakeNotificationService sms = new FakeNotificationService();

        NotificationProcessor processor
                = new NotificationProcessor(email, sms);

        Map<String, Object> data = new HashMap<>();
        data.put("notifyChannel", "SMS");

        processor.processNotification(data, "user@example.com", 750.00);

        assertEquals(1, sms.callCount);
        assertEquals("0300-0000000", sms.receivedRecipient);
    }

    @Test
    void unsupportedChannelSendsNoNotification() {

        FakeNotificationService email = new FakeNotificationService();
        FakeNotificationService sms = new FakeNotificationService();

        NotificationProcessor processor
                = new NotificationProcessor(email, sms);

        Map<String, Object> data = new HashMap<>();
        data.put("notifyChannel", "WHATSAPP");

        processor.processNotification(data, "user@example.com", 1000.00);

        assertEquals(0, email.callCount);
        assertEquals(0, sms.callCount);
    }
}
