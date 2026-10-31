/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.List;
import java.util.Map;

public class OrderCalculator {

    public double calculateRawTotal(
            List<Map<String, Object>> items) {

        double rawTotal = 0.0;

        for (Map<String, Object> item : items) {

            double price = (double) item.get("price");
            int quantity = (int) item.get("quantity");

            rawTotal += price * quantity;
        }

        return rawTotal;
    }
}
