package com.assessment.kam.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDTO {

    @NotNull(message = "Order date cannot be null")
    @PastOrPresent(message = "Order date must be in the past or present")
    private LocalDateTime orderDate;

    @Positive(message = "Order value must be positive")
    private double totalAmount;

    @NotNull(message = "Lead ID cannot be null")
    private Long leadId;

    public OrderDTO(LocalDateTime orderDate, double totalAmount, Long leadId) {
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.leadId = leadId;
    }
}
