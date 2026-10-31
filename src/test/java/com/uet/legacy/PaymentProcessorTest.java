/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorTest {

    private final PaymentProcessor processor
            = new PaymentProcessor();

    @Test
    void acceptsValidCreditCard() {
        Map<String, Object> data = new HashMap<>();
        data.put("cardNumber", "1234567890123456");

        assertTrue(processor.processPayment(
                "CREDIT_CARD", 1000, data
        ));
    }

    @Test
    void rejectsInvalidCreditCard() {
        Map<String, Object> data = new HashMap<>();
        data.put("cardNumber", "123456789012");

        assertFalse(processor.processPayment(
                "CREDIT_CARD", 1000, data
        ));
    }

    @Test
    void acceptsValidEasyPaisaNumber() {
        Map<String, Object> data = new HashMap<>();
        data.put("mobileNumber", "03001234567");

        assertTrue(processor.processPayment(
                "EASYPAISA", 1000, data
        ));
    }

    @Test
    void rejectsEasyPaisaNumberWithIncorrectLength() {
        Map<String, Object> data = new HashMap<>();
        data.put("mobileNumber", "0300123456");

        assertFalse(processor.processPayment(
                "EASYPAISA", 1000, data
        ));
    }

    @Test
    void acceptsEasyPaisaNumberStartingWith03AndLength11() {
        Map<String, Object> data = new HashMap<>();
        data.put("mobileNumber", "03abcdefghi");

        assertTrue(processor.processPayment(
                "EASYPAISA", 1000, data
        ));
    }

    @Test
    void acceptsCashOnDeliveryAt25000() {
        assertTrue(processor.processPayment(
                "CASH_ON_DELIVERY", 25000, new HashMap<>()
        ));
    }

    @Test
    void rejectsCashOnDeliveryAbove25000() {
        assertFalse(processor.processPayment(
                "CASH_ON_DELIVERY", 25000.01, new HashMap<>()
        ));
    }

    @Test
    void rejectsUnsupportedPaymentMethod() {
        assertFalse(processor.processPayment(
                "PAYPAL", 1000, new HashMap<>()
        ));
    }
}
