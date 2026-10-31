/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

public class TaxCalculator {

    public double getTaxRate(String country) {

        double taxRate = 0.15;

        if (country.equals("PK")) {

            taxRate = 0.17;

        } else if (country.equals("US")) {

            taxRate = 0.08;

        } else if (country.equals("UK")) {

            taxRate = 0.20;
        }

        return taxRate;
    }

    public double calculateTax(
            double netAmount,
            String country) {

        return netAmount * getTaxRate(country);
    }
}
