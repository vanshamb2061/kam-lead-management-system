package com.assessment.kam.strategy;

import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.model.Lead;

import java.util.List;

interface PerformanceStrategy {
    List<Long> getWellPerformingLeads();

    List<Long> getWellPerformingLeadsCount(Integer count);

    List<Long> getUnderPerformingLeads();

    List<Long> getUnderPerformingLeadsCount(Integer count);
}
