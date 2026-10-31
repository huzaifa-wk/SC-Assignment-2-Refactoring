/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.Map;

public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount, Map<String, Object> orderData) {

        String cardNumber = (String) orderData.get("cardNumber");

        if (cardNumber != null && cardNumber.matches("\\d{16}")) {
            System.out.println("Credit Card payment successful.");
            return true;
        }

        return false;
    }
}
