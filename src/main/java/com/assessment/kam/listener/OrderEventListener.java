package com.assessment.kam.listener;

import com.assessment.kam.service.PerformanceMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderEventListener {

    @Autowired
    private PerformanceMetricsService performanceMetricsService;

    public void onOrderPlaced(Long leadId, Long orderId, double orderValue, LocalDateTime orderDate) {
        performanceMetricsService.updateMetricsForOrder(leadId, orderId, orderValue, orderDate);
    }
}
