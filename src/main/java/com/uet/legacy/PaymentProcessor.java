/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.Map;

public class PaymentProcessor {

    public boolean processPayment(
            String paymentMethod,
            double amount,
            Map<String, Object> orderData) {

        PaymentStrategy strategy;

        switch (paymentMethod) {

            case "CREDIT_CARD":
                strategy = new CreditCardPayment();
                break;

            case "EASYPAISA":
                strategy = new EasyPaisaPayment();
                break;

            case "CASH_ON_DELIVERY":
                strategy = new CashOnDeliveryPayment();
                break;

            default:
                return false;
        }

        return strategy.pay(amount, orderData);
    }
}
