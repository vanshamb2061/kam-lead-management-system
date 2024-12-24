package com.assessment.kam.factory;

import com.assessment.kam.model.PerformanceMetrics;

import java.time.LocalDateTime;

public class PerformanceMetricsFactory {
    public static PerformanceMetrics createPerformanceMetrics(Long leadID, int orderCount, double totalRevenue, double averageOrderValue, LocalDateTime lastOrderDate){
        return new PerformanceMetrics(leadID,orderCount,totalRevenue,averageOrderValue,lastOrderDate);
    }
}
