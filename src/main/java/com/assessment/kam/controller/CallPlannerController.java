package com.assessment.kam.controller;

import com.assessment.kam.dto.CallPlannerDTO;
import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.service.CallPlannerService;
import com.assessment.kam.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/call-planner")
public class CallPlannerController {

    @Autowired
    private CallPlannerService callPlannerService;

    @Autowired
    private LeadService leadService;

    @PutMapping("/update-frequency/{leadId}")
    public ResponseEntity<CallPlannerDTO> updateCallFrequency(@PathVariable Long leadId,
                                                              @RequestBody CallPlannerDTO callPlannerDTO) {
            CallPlannerDTO updatedCallPlannerDTO = callPlannerService.updateCallFrequency(leadId, callPlannerDTO.getCallFrequency());
            return ResponseEntity.ok(updatedCallPlannerDTO);
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<CallPlannerDTO> getCallPlannerForLead(@PathVariable Long leadId) {
        CallPlannerDTO callPlannerDTO = callPlannerService.getCallPlannerForLead(leadId);
        return ResponseEntity.ok(callPlannerDTO);
    }

    @GetMapping("/last-call/{leadId}")
    public ResponseEntity<LocalDate> getLastCallForLead(@PathVariable Long leadId) {
        LocalDate date = callPlannerService.getLastCallForLead(leadId);
        return ResponseEntity.ok(date);
    }

    @GetMapping("/leads-to-call-today")
    public ResponseEntity<List<LeadDTO>> getLeadsRequiringCallToday() {
        List<Long> leadIds = callPlannerService.getLeadsRequiringCallToday();
        List<LeadDTO> leads = new ArrayList<>();
        for(Long leadId: leadIds){
            leads.add(leadService.getLeadDTOById(leadId));
        }
        return ResponseEntity.ok(leads);
    }
}