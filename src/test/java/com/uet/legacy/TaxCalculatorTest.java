/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {

    private final TaxCalculator calculator
            = new TaxCalculator();

    // Tax rate tests
    @Test
    void pakistanGets17PercentTax() {
        assertEquals(0.17, calculator.getTaxRate("PK"), 0.001);
    }

    @Test
    void unitedStatesGets8PercentTax() {
        assertEquals(0.08, calculator.getTaxRate("US"), 0.001);
    }

    @Test
    void unitedKingdomGets20PercentTax() {
        assertEquals(0.20, calculator.getTaxRate("UK"), 0.001);
    }

    @Test
    void unknownCountryGetsDefault15PercentTax() {
        assertEquals(0.15, calculator.getTaxRate("UAE"), 0.001);
    }

    // Tax calculation tests
    @Test
    void calculatesPakistanTaxCorrectly() {
        assertEquals(170.0, calculator.calculateTax(1000, "PK"), 0.001);
    }

    @Test
    void calculatesUnitedStatesTaxCorrectly() {
        assertEquals(80.0, calculator.calculateTax(1000, "US"), 0.001);
    }

    @Test
    void calculatesUnitedKingdomTaxCorrectly() {
        assertEquals(200.0, calculator.calculateTax(1000, "UK"), 0.001);
    }

    @Test
    void calculatesDefaultTaxCorrectly() {
        assertEquals(150.0, calculator.calculateTax(1000, "UAE"), 0.001);
    }
}
