/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.Map;

public class CashOnDeliveryPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount, Map<String, Object> orderData) {

        if (amount <= 25000) {
            System.out.println("Cash on Delivery selected.");
            return true;
        }

        return false;
    }
}
