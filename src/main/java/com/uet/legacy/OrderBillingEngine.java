/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.List;
import java.util.Map;

/**
 *
 * @author codez
 */
public class OrderBillingEngine {

    private final DiscountCalculator discountCalculator
            = new DiscountCalculator();

    private final TaxCalculator taxCalculator
            = new TaxCalculator();

    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    private final NotificationProcessor notificationProcessor
            = new NotificationProcessor();

    private final OrderRepository orderRepository = new OrderRepository();

    private final OrderValidator orderValidator = new OrderValidator();

    private final OrderCalculator orderCalculator = new OrderCalculator();

    public boolean processOrder(Map<String, Object> orderData) {
        // 1. Input Validation
        if (!orderValidator.isValidOrder(orderData)) {
            return false;
        }

        String email = (String) orderData.get("customerEmail");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items
                = (List<Map<String, Object>>) orderData.get("items");

        // 2. Total & Discount Calculation (Long method, primitive types, magic numbers)
        double rawTotal = orderCalculator.calculateRawTotal(items);

        // Hardcoded discount rules with magic percentages
        String customerType
                = (String) orderData.getOrDefault("customerType", "REGULAR");

        double discount = discountCalculator.calculateDiscount(
                rawTotal,
                customerType,
                items.size()
        );

        double netAmount = rawTotal - discount;

        // Hardcoded Tax Rules by Country
        String country
                = (String) orderData.getOrDefault("country", "PK");

        netAmount = rawTotal - discount;

        double tax = taxCalculator.calculateTax(
                netAmount,
                country
        );

        double payableAmount = netAmount + tax;

        // 3. Payment Processing using Strategy Pattern
        String paymentMethod
                = (String) orderData.getOrDefault("paymentMethod", "CASH");

        String paymentStatus = "FAILED";

        boolean paymentSuccessful = paymentProcessor.processPayment(
                paymentMethod,
                payableAmount,
                orderData
        );

        if (!paymentSuccessful) {
            return false;
        }

        if (paymentMethod.equals("CASH_ON_DELIVERY")) {
            paymentStatus = "PENDING_CASH";
        } else {
            paymentStatus = "PAID";
        }

        orderRepository.saveOrderLog(
                email,
                payableAmount,
                paymentStatus
        );

        // 5. Direct Notification Dispatch (Coupled side-effect)
        notificationProcessor.processNotification(
                orderData,
                email,
                payableAmount
        );
        return true;
    }

    public void setLogFilePath(String logFilePath) {
        orderRepository.setLogFilePath(logFilePath);
    }

    public String getLogFilePath() {
        return orderRepository.getLogFilePath();
    }
}
