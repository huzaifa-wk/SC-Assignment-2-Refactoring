/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountCalculatorTest {

    private final DiscountCalculator calculator
            = new DiscountCalculator();

    @Test
    void regularCustomerGetsFlatDiscountAbove10000() {
        double discount = calculator.calculateDiscount(
                11000, "Regular", 1
        );

        assertEquals(500.0, discount, 0.001);
    }

    @Test
    void regularCustomerGetsNoDiscountAt10000() {
        double discount = calculator.calculateDiscount(
                10000, "Regular", 1
        );

        assertEquals(0.0, discount, 0.001);
    }

    @Test
    void vipCustomerGets15PercentDiscountAbove5000() {
        double discount = calculator.calculateDiscount(
                6000, "VIP", 1
        );

        assertEquals(900.0, discount, 0.001);
    }

    @Test
    void vipCustomerGets10PercentDiscountAt5000OrBelow() {
        double discount = calculator.calculateDiscount(
                4000, "VIP", 1
        );

        assertEquals(400.0, discount, 0.001);
    }

    @Test
    void studentGets8PercentDiscount() {
        double discount = calculator.calculateDiscount(
                1000, "STUDENT", 1
        );

        assertEquals(80.0, discount, 0.001);
    }

    @Test
    void corporateCustomerWith10ItemsGets20PercentDiscount() {
        double discount = calculator.calculateDiscount(
                1000, "CORPORATE", 10
        );

        assertEquals(200.0, discount, 0.001);
    }

    @Test
    void corporateCustomerWithLessThan10ItemsGets5PercentDiscount() {
        double discount = calculator.calculateDiscount(
                1000, "CORPORATE", 5
        );

        assertEquals(50.0, discount, 0.001);
    }
}
