/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uet.legacy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderBillingEngineTest {

    private OrderBillingEngine engine;

    @TempDir
    Path tempDir;

    private Path logFile;

    @BeforeEach
    void setUp() {
        engine = new OrderBillingEngine();

        logFile = tempDir.resolve("test_order_audit.log");

        engine.setLogFilePath(logFile.toString());
    }

    // Helper method: Creates an individual order item
    private Map<String, Object> createItem(double price, int quantity) {

        Map<String, Object> item = new HashMap<>();

        item.put("price", price);
        item.put("quantity", quantity);

        return item;
    }

    // Helper method: Creates order data
    private Map<String, Object> createOrder(
            String customerType,
            List<Map<String, Object>> items) {

        Map<String, Object> order = new HashMap<>();

        order.put("customerEmail", "student@example.com");
        order.put("customerType", customerType);
        order.put("country", "PK");
        order.put("paymentMethod", "CASH_ON_DELIVERY");
        order.put("items", items);

        return order;
    }

    // Helper method: Checks the calculated payable amount
    private void verifyPayableAmount(
            Map<String, Object> order,
            double expectedAmount) throws IOException {

        boolean result = engine.processOrder(order);

        assertTrue(result, "Order should be processed successfully");

        String logContent = Files.readString(logFile);

        assertTrue(
                logContent.contains("Payable: PKR " + expectedAmount),
                "Expected payable amount: " + expectedAmount
        );
    }

    // TEST 1: Regular customer discount
    @Test
    void testRegularCustomerDiscount() throws IOException {

        List<Map<String, Object>> items = List.of(
                createItem(1000.0, 1)
        );

        Map<String, Object> order = createOrder("REGULAR", items);

        // 1000 + 17% tax = 1170
        verifyPayableAmount(order, 1170.0);
    }

    // TEST 2: VIP customer above 5000 (15% discount)
    @Test
    void testVipDiscountAbove5000() throws IOException {

        List<Map<String, Object>> items = List.of(
                createItem(6000.0, 1)
        );

        Map<String, Object> order = createOrder("VIP", items);

        // 6000 - 15% = 5100
        // 5100 + 17% tax = 5967
        verifyPayableAmount(order, 5967.0);
    }

    // TEST 3: VIP customer below 5000 (10% discount)
    @Test
    void testVipDiscountBelow5000() throws IOException {

        List<Map<String, Object>> items = List.of(
                createItem(4000.0, 1)
        );

        Map<String, Object> order = createOrder("VIP", items);

        // 4000 - 10% = 3600
        // 3600 + 17% tax = 4212
        verifyPayableAmount(order, 4212.0);
    }

    // TEST 4: Student discount (8%)
    @Test
    void testStudentDiscount() throws IOException {

        List<Map<String, Object>> items = List.of(
                createItem(1000.0, 1)
        );

        Map<String, Object> order = createOrder("STUDENT", items);

        // 1000 - 8% = 920
        // 920 + 17% tax = 1076.4
        verifyPayableAmount(order, 1076.4);
    }

    // TEST 5: Corporate bulk discount (20%)
    @Test
    void testCorporateBulkDiscount() throws IOException {

        List<Map<String, Object>> items = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            items.add(createItem(100.0, 1));
        }

        Map<String, Object> order = createOrder("CORPORATE", items);

        // 1000 - 20% = 800
        // 800 + 17% tax = 936
        verifyPayableAmount(order, 936.0);
    }

    // TEST 6: Corporate standard discount (5%)
    @Test
    void testCorporateStandardDiscount() throws IOException {

        List<Map<String, Object>> items = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            items.add(createItem(100.0, 1));
        }

        Map<String, Object> order = createOrder("CORPORATE", items);

        // 500 - 5% = 475
        // 475 + 17% tax = 555.75
        verifyPayableAmount(order, 555.75);
    }

    // TEST 7: Regular customer flat discount above 10000
    @Test
    void testRegularFlatDiscountAbove10000() throws IOException {

        List<Map<String, Object>> items = List.of(
                createItem(11000.0, 1)
        );

        Map<String, Object> order = createOrder("REGULAR", items);

        // 11000 - 500 = 10500
        // 10500 + 17% tax = 12285
        verifyPayableAmount(order, 12285.0);
    }

    @Test
    void testCreditCardPaymentSuccess() throws IOException {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(1000.0, 1))
        );

        order.put("paymentMethod", "CREDIT_CARD");
        order.put("cardNumber", "1234567890123456");

        boolean result = engine.processOrder(order);

        assertTrue(result);

        String logContent = Files.readString(logFile);

        assertTrue(logContent.contains("Status: PAID"));
    }

    @Test
    void testInvalidCreditCard() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(1000.0, 1))
        );

        order.put("paymentMethod", "CREDIT_CARD");
        order.put("cardNumber", "12345");

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testEasyPaisaPaymentSuccess() throws IOException {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(1000.0, 1))
        );

        order.put("paymentMethod", "EASYPAISA");
        order.put("mobileNumber", "03001234567");

        boolean result = engine.processOrder(order);

        assertTrue(result);

        String logContent = Files.readString(logFile);

        assertTrue(logContent.contains("Status: PAID"));
    }

    @Test
    void testInvalidEasyPaisaNumber() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(1000.0, 1))
        );

        order.put("paymentMethod", "EASYPAISA");
        order.put("mobileNumber", "12345");

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testCashOnDeliverySuccess() throws IOException {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(1000.0, 1))
        );

        boolean result = engine.processOrder(order);

        assertTrue(result);

        String logContent = Files.readString(logFile);

        assertTrue(logContent.contains("Status: PENDING_CASH"));
    }

    @Test
    void testCashOnDeliveryAboveLimit() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(22000.0, 1))
        );

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testEmptyOrderData() {

        Map<String, Object> order = new HashMap<>();

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testNullOrderData() {

        boolean result = engine.processOrder(null);

        assertFalse(result);
    }

    @Test
    void testInvalidCustomerEmail() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(1000.0, 1))
        );

        order.put("customerEmail", "invalid-email");

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testEmptyShoppingCart() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                new ArrayList<>()
        );

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testNegativeItemPrice() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(-100.0, 1))
        );

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testNegativeItemQuantity() {

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(createItem(100.0, -2))
        );

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testMissingItemPrice() {

        Map<String, Object> item = new HashMap<>();
        item.put("quantity", 2);

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(item)
        );

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

    @Test
    void testMissingItemQuantity() {

        Map<String, Object> item = new HashMap<>();
        item.put("price", 100.0);

        Map<String, Object> order = createOrder(
                "REGULAR",
                List.of(item)
        );

        boolean result = engine.processOrder(order);

        assertFalse(result);
    }

}
