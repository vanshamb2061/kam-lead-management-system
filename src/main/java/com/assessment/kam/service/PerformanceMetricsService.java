package com.assessment.kam.service;

import com.assessment.kam.factory.PerformanceMetricsFactory;
import com.assessment.kam.model.Order;
import com.assessment.kam.model.PerformanceMetrics;
import com.assessment.kam.repository.OrderRepository;
import com.assessment.kam.repository.PerformanceMetricsRepository;
import com.assessment.kam.strategy.OrderBasedPerformanceStrategy;
import com.assessment.kam.strategy.PerformanceContext;
import com.assessment.kam.strategy.RevenueBasedPerformanceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PerformanceMetricsService {

    @Autowired
    private PerformanceMetricsRepository performanceMetricsRepository;

    @Autowired
    private PerformanceContext performanceContext;

    @Autowired
    private RevenueBasedPerformanceStrategy revenueStrategy;

    @Autowired
    private OrderBasedPerformanceStrategy orderStrategy;

    @Autowired
    private OrderRepository orderRepository;

    public List<LocalDateTime> getOrderDatesForLead(Long leadId) {
        List<Order> orders = orderRepository.findByLeadId(leadId);
        return orders.stream()
                .map(Order::getOrderDate)
                .collect(Collectors.toList());
    }

    public double getAverageOrdersPerMonth(Long leadId) {
        List<Order> orders = orderRepository.findByLeadId(leadId);
        if (orders.isEmpty()) {
            return 0.0;
        }

        Map<YearMonth, Long> ordersByMonth = orders.stream()
                .map(order -> YearMonth.from(order.getOrderDate()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return ordersByMonth.values().stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);
    }

    public void setStrategy(String strategyType) {
        if ("revenue".equalsIgnoreCase(strategyType)) {
            performanceContext.setPerformanceStrategy(revenueStrategy);
        } else if ("orders".equalsIgnoreCase(strategyType)) {
            performanceContext.setPerformanceStrategy(orderStrategy);
        } else {
            throw new IllegalArgumentException("Invalid strategy type: " + strategyType);
        }
    }


    @Transactional
    public void updateMetricsForOrder(Long leadId, Long orderId, double orderValue, LocalDateTime orderDate) {
        Optional<PerformanceMetrics> metricsOptional = performanceMetricsRepository.findByLeadId(leadId);
        PerformanceMetrics metrics;
        if(metricsOptional.isPresent()) {
            metrics = metricsOptional.get();
        } else {
            metrics = PerformanceMetricsFactory.createPerformanceMetrics(leadId,0,0,0,LocalDateTime.now());
        }
        metrics.setLeadId(leadId);
        metrics.setOrderCount(metrics.getOrderCount() + 1);
        metrics.setTotalRevenue(metrics.getTotalRevenue() + orderValue);
        metrics.setAverageOrderValue(metrics.getTotalRevenue() / metrics.getOrderCount());
        metrics.setLastOrderDate(orderDate);
        performanceMetricsRepository.save(metrics);
    }

    public List<Long> getWellPerformingLeads() {
        return performanceContext.getWellPerformingLeads();
    }

    public List<Long> getWellPerformingLeads(int count) {
        return performanceContext.getWellPerformingLeads(count);
    }

    public List<Long> getUnderPerformingLeads() {
        return performanceContext.getUnderPerformingLeads();
    }

    public List<Long> getUnderPerformingLeads(int count) {
        return performanceContext.getUnderPerformingLeads(count);
    }
}

