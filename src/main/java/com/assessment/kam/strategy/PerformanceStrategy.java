package com.assessment.kam.strategy;

import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.model.Lead;
import com.assessment.kam.model.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

interface PerformanceStrategy {

    int getWellPerformingThreshold();
    int getUnderPerformingThreshold();
    List<Long> getWellPerformingLeads();

    List<Long> getWellPerformingLeadsCount(Integer count);

    List<Long> getUnderPerformingLeads();

    List<Long> getUnderPerformingLeadsCount(Integer count);
}
