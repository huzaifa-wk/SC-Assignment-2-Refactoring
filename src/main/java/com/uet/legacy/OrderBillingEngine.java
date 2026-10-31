/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.List;
import java.util.Map;

public class OrderBillingEngine {

    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;
    private final PaymentProcessor paymentProcessor;
    private final NotificationProcessor notificationProcessor;
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderCalculator orderCalculator;

    // Default constructor preserves existing usage
    public OrderBillingEngine() {

        this(
                new DiscountCalculator(),
                new TaxCalculator(),
                new PaymentProcessor(),
                new NotificationProcessor(),
                new OrderRepository(),
                new OrderValidator(),
                new OrderCalculator()
        );
    }

    // Constructor Dependency Injection
    public OrderBillingEngine(
            DiscountCalculator discountCalculator,
            TaxCalculator taxCalculator,
            PaymentProcessor paymentProcessor,
            NotificationProcessor notificationProcessor,
            OrderRepository orderRepository,
            OrderValidator orderValidator,
            OrderCalculator orderCalculator) {

        this.discountCalculator = discountCalculator;
        this.taxCalculator = taxCalculator;
        this.paymentProcessor = paymentProcessor;
        this.notificationProcessor = notificationProcessor;
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderCalculator = orderCalculator;
    }

    public boolean processOrder(Map<String, Object> orderData) {

        if (!orderValidator.isValidOrder(orderData)) {
            return false;
        }

        String email = (String) orderData.get("customerEmail");

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items
                = (List<Map<String, Object>>) orderData.get("items");

        double rawTotal
                = orderCalculator.calculateRawTotal(items);

        String customerType
                = (String) orderData.getOrDefault("customerType", "REGULAR");

        double discount = discountCalculator.calculateDiscount(
                rawTotal,
                customerType,
                items.size()
        );

        double netAmount = rawTotal - discount;

        String country
                = (String) orderData.getOrDefault("country", "PK");

        double tax = taxCalculator.calculateTax(
                netAmount,
                country
        );

        double payableAmount = netAmount + tax;

        String paymentMethod
                = (String) orderData.getOrDefault("paymentMethod", "CASH");

        boolean paymentSuccessful = paymentProcessor.processPayment(
                paymentMethod,
                payableAmount,
                orderData
        );

        if (!paymentSuccessful) {
            return false;
        }

        String paymentStatus;

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
