/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.Map;

public class NotificationProcessor {

    private final NotificationService emailNotification;
    private final NotificationService smsNotification;

    // Default constructor preserves existing application behavior
    public NotificationProcessor() {
        this(
                new EmailNotification(),
                new SmsNotification()
        );
    }

    // Constructor injection for independent testing
    public NotificationProcessor(
            NotificationService emailNotification,
            NotificationService smsNotification) {

        this.emailNotification = emailNotification;
        this.smsNotification = smsNotification;
    }

    public void processNotification(
            Map<String, Object> orderData,
            String email,
            double payableAmount) {

        String notifyChannel
                = (String) orderData.getOrDefault("notifyChannel", "EMAIL");

        String formattedAmount = String.format("%.2f", payableAmount);

        if (notifyChannel.equals("EMAIL")) {

            String message = "Your order of PKR "
                    + formattedAmount + " is confirmed!";

            emailNotification.sendNotification(email, message);

        } else if (notifyChannel.equals("SMS")) {

            String phone = (String) orderData.getOrDefault(
                    "phone", "0300-0000000"
            );

            String message = "Order confirmed! Paid: PKR "
                    + formattedAmount;

            smsNotification.sendNotification(phone, message);
        }
    }
}
