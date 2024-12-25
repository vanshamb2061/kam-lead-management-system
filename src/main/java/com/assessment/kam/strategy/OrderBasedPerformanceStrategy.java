package com.assessment.kam.strategy;

import com.assessment.kam.model.Lead;
import com.assessment.kam.model.PerformanceMetrics;
import com.assessment.kam.repository.PerformanceMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderBasedPerformanceStrategy implements PerformanceStrategy {

    @Autowired
    private PerformanceMetricsRepository performanceMetricsRepository;

    @Value("${performance.order.under-performing.threshold}")
    private int underPerformingThreshold;

    @Value("${performance.order.well-performing.threshold}")
    private int wellPerformingThreshold;
    @Override
    public int getWellPerformingThreshold() {
        return wellPerformingThreshold;
    }

    @Override
    public int getUnderPerformingThreshold() {
        return underPerformingThreshold;
    }

    @Override
    public List<Long> getWellPerformingLeads() {
        int orderThreshold = getWellPerformingThreshold();
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeads = metrics.stream()
                .filter(metric -> metric.getOrderCount() > orderThreshold)
                .map(PerformanceMetrics::getLeadId)
                .toList();
        return wellPerformingLeads;
    }

    @Override
    public List<Long> getWellPerformingLeadsCount(Integer count) {
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeads = metrics.stream()
                .sorted(Comparator.comparing(PerformanceMetrics::getOrderCount).reversed())
                .map(PerformanceMetrics::getLeadId)
                .limit(count)
                .toList();
        return wellPerformingLeads;
    }

    @Override
    public List<Long> getUnderPerformingLeads() {
        int orderThreshold = getUnderPerformingThreshold();
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeads = metrics.stream()
                .filter(metric -> metric.getOrderCount() < orderThreshold)
                .map(PerformanceMetrics::getLeadId)
                .toList();
        return wellPerformingLeads;
    }

    @Override
    public List<Long> getUnderPerformingLeadsCount(Integer count) {
        int orderThreshold = 50;
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeads = metrics.stream()
                .sorted(Comparator.comparing(PerformanceMetrics::getOrderCount))
                .map(PerformanceMetrics::getLeadId)
                .limit(count)
                .toList();
        return wellPerformingLeads;
    }
}
