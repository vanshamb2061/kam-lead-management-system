package com.assessment.kam.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDTO {

    private LocalDateTime orderDate;
    private double totalAmount;
    private Long leadId;

    public OrderDTO(LocalDateTime orderDate, double totalAmount, Long leadId) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.leadId = leadId;
    }
}
