package com.assessment.kam.controller;

import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.model.Lead;
import com.assessment.kam.service.LeadService;
import com.assessment.kam.service.PerformanceMetricsService;
import com.assessment.kam.strategy.OrderBasedPerformanceStrategy;
import com.assessment.kam.strategy.PerformanceContext;
import com.assessment.kam.strategy.RevenueBasedPerformanceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/performance")
public class PerformanceController {

    @Autowired
    private RevenueBasedPerformanceStrategy revenueBasedPerformanceStrategy;

    @Autowired
    private PerformanceContext performanceContext;

    @Autowired
    private OrderBasedPerformanceStrategy orderBasedPerformanceStrategy;

    @Autowired
    private LeadService leadService;

    @Autowired
    private PerformanceMetricsService performanceMetricsService;

    @PostMapping("/strategy")
    public ResponseEntity<String> updateStrategy(@RequestParam String strategyType) {
        performanceMetricsService.setStrategy(strategyType);
        return ResponseEntity.ok("Performance strategy updated to: " + strategyType);
    }

    // Endpoint to get well-performing leads based on the chosen strategy
    @GetMapping("/well-performing")
    public ResponseEntity<List<LeadDTO>> getWellPerformingAccounts() {
        List<Long> leadIds = performanceMetricsService.getWellPerformingLeads();
        List<LeadDTO> leads = new ArrayList<>();
        for(Long leadId: leadIds){
            leads.add(leadService.getLeadDTOById(leadId));
        }
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/well-performing/{count}")
    public ResponseEntity<List<LeadDTO>> getWellPerformingAccounts(@PathVariable Integer count) {
        List<Long> leadIds = performanceMetricsService.getWellPerformingLeads(count);
        List<LeadDTO> leads = new ArrayList<>();
        for(Long leadId: leadIds){
            leads.add(leadService.getLeadDTOById(leadId));
        }
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/under-performing")
    public ResponseEntity<List<LeadDTO>> getUnderPerformingAccounts() {
        List<Long> leadIds = performanceMetricsService.getUnderPerformingLeads();
        List<LeadDTO> leads = new ArrayList<>();
        for(Long leadId: leadIds){
            leads.add(leadService.getLeadDTOById(leadId));
        }
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/under-performing/{count}")
    public ResponseEntity<List<LeadDTO>> getUnderPerformingAccounts(@PathVariable Integer count) {
        List<Long> leadIds = performanceMetricsService.getUnderPerformingLeads(count);
        List<LeadDTO> leads = new ArrayList<>();
        for(Long leadId: leadIds){
            leads.add(leadService.getLeadDTOById(leadId));
        }
        return ResponseEntity.ok(leads);
    }

    @GetMapping("/orders/dates/{leadId}")
    public List<LocalDateTime> getOrderDatesForLead(@PathVariable Long leadId) {
        return performanceMetricsService.getOrderDatesForLead(leadId);
    }

    @GetMapping("/orders/average-monthly/{leadId}")
    public double getAverageOrdersPerMonth(@PathVariable Long leadId) {
        return performanceMetricsService.getAverageOrdersPerMonth(leadId);
    }
}
