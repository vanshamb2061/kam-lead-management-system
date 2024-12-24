package com.assessment.kam.strategy;

import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.model.Lead;
import com.assessment.kam.model.PerformanceMetrics;
import com.assessment.kam.repository.PerformanceMetricsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevenueBasedPerformanceStrategy implements PerformanceStrategy {

    @Autowired
    private PerformanceMetricsRepository performanceMetricsRepository;

    @Override
    public List<Long> getWellPerformingLeads() {
        double revenueThreshold = 10000.0;
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeadIds = metrics.stream()
                .filter(metric -> metric.getTotalRevenue() > revenueThreshold)
                .map(PerformanceMetrics::getLeadId)
                .toList();

        return wellPerformingLeadIds;
    }

    @Override
    public List<Long> getWellPerformingLeadsCount(Integer count) {
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeadIds = metrics.stream()
                .sorted(Comparator.comparing(PerformanceMetrics::getTotalRevenue).reversed())
                .map(PerformanceMetrics::getLeadId)
                .limit(count)
                .toList();
        return wellPerformingLeadIds;
    }


    @Override
    public List<Long> getUnderPerformingLeads() {
        double revenueThreshold = 10000.0;
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeadIds = metrics.stream()
                .filter(metric -> metric.getTotalRevenue() < revenueThreshold)
                .map(PerformanceMetrics::getLeadId)
                .toList();

        return wellPerformingLeadIds;
    }

    @Override
    public List<Long> getUnderPerformingLeadsCount(Integer count) {
        List<PerformanceMetrics> metrics = performanceMetricsRepository.findAll();

        List<Long> wellPerformingLeadIds = metrics.stream()
                .sorted(Comparator.comparing(PerformanceMetrics::getTotalRevenue))
                .map(PerformanceMetrics::getLeadId)
                .limit(count)
                .toList();
        return wellPerformingLeadIds;
    }

}

