/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class OrderRepository {

    private String logFilePath = "order_audit.log";

    public void saveOrderLog(
            String email,
            double payableAmount,
            String paymentStatus) {

        try (FileWriter writer = new FileWriter(logFilePath, true)) {

            writer.write(
                    LocalDateTime.now()
                    + " | Order for: " + email
                    + " | Payable: PKR " + payableAmount
                    + " | Status: " + paymentStatus + "\n"
            );

        } catch (IOException e) {

            System.out.println(
                    "Failed to write order log: " + e.getMessage()
            );
        }
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}
