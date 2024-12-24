package com.assessment.kam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PerformanceMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long leadId;

    private int orderCount;
    private double totalRevenue;
    private double averageOrderValue;
    private LocalDateTime lastOrderDate;

    public PerformanceMetrics(){
    }

    public PerformanceMetrics(Long leadID, int orderCount, double totalRevenue, double averageOrderValue, LocalDateTime lastOrderDate){
        this.leadId = leadID;
        this.orderCount = orderCount;
        this.totalRevenue = totalRevenue;
        this.averageOrderValue = averageOrderValue;
        this.lastOrderDate = lastOrderDate;
    }
}
