package com.assessment.kam.strategy;

import com.assessment.kam.model.Lead;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PerformanceContext {

    private PerformanceStrategy performanceStrategy;

    public void setPerformanceStrategy(PerformanceStrategy performanceStrategy) {
        this.performanceStrategy = performanceStrategy;
    }

    public List<Long> getWellPerformingLeads() {
        if (performanceStrategy == null) {
            throw new IllegalStateException("Performance strategy is not set.");
        }
        return performanceStrategy.getWellPerformingLeads();
    }

    public List<Long> getWellPerformingLeads(Integer count) {
        if (performanceStrategy == null) {
            throw new IllegalStateException("Performance strategy is not set.");
        }
        return performanceStrategy.getWellPerformingLeadsCount(count);
    }

    public List<Long> getUnderPerformingLeads() {
        if (performanceStrategy == null) {
            throw new IllegalStateException("Performance strategy is not set.");
        }
        return performanceStrategy.getUnderPerformingLeads();
    }

    public List<Long> getUnderPerformingLeads(Integer count) {
        if (performanceStrategy == null) {
            throw new IllegalStateException("Performance strategy is not set.");
        }
        return performanceStrategy.getUnderPerformingLeadsCount(count);
    }
}


