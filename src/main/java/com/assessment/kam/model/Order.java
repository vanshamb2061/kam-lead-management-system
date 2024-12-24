package com.assessment.kam.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderDate;
    private double totalAmount;
    @ManyToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;

    public Order(){
    }

    public Order(LocalDateTime orderDate, Double totalAmount, Lead lead){
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.lead = lead;
    }
}
