/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uet.legacy;

import java.util.Map;

public interface PaymentStrategy {

    boolean pay(double amount, Map<String, Object> orderData);
}
