/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.Map;

public class EasyPaisaPayment implements PaymentStrategy {

    @Override
    public boolean pay(double amount, Map<String, Object> orderData) {

        String mobileNumber = (String) orderData.get("mobileNumber");

        if (mobileNumber != null
                && mobileNumber.length() == 11
                && mobileNumber.startsWith("03")) {
            {
                System.out.println("EasyPaisa payment successful.");
                return true;
            }
        }
        return false;
    }
}
