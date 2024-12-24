package com.assessment.kam.factory;

import com.assessment.kam.model.CallPlanner;
import com.assessment.kam.model.Lead;
import com.assessment.kam.model.Order;

import java.time.LocalDateTime;

public class OrderFactory {
    public static Order createOrder(LocalDateTime orderDate, Double totalAmount, Lead lead){
        return new Order(orderDate, totalAmount, lead);
    }
}
