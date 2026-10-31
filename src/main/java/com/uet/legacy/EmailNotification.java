/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String recipient, String message) {

        System.out.println(
                "[EMAIL DISPATCHED] To: " + recipient + " | " + message
        );
    }
}
