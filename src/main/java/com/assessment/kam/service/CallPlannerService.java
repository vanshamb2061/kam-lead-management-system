package com.assessment.kam.service;

import com.assessment.kam.dto.CallPlannerDTO;
import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.enums.CallFrequency;
import com.assessment.kam.factory.CallPlannerFactory;
import com.assessment.kam.model.CallPlanner;
import com.assessment.kam.model.Lead;
import com.assessment.kam.repository.CallPlannerRepository;
import org.aspectj.weaver.ast.Call;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CallPlannerService {

    @Autowired
    private CallPlannerRepository callPlannerRepository;

    public CallPlannerDTO getCallPlannerForLead(Long leadId) {
        CallPlanner callPlanner = callPlannerRepository.findByLeadId(leadId)
                .orElseThrow(() -> new RuntimeException("Call planner not found for leadId: " + leadId));
        return convertToDTO(callPlanner);
    }

    public LocalDate getLastCallForLead(Long leadId) {
        CallPlanner callPlanner = callPlannerRepository.findByLeadId(leadId)
                .orElseThrow(() -> new RuntimeException("Call planner not found for leadId: " + leadId));
        return callPlanner.getLastCallDate();
    }

    @Transactional
    public void createCallPlannerForLead(Lead lead, CallFrequency callFrequency) {
        CallPlanner callPlanner = CallPlannerFactory.createCallPlanner(lead, callFrequency, LocalDate.now(), calculateNextCallDate(LocalDate.now(), callFrequency));
        callPlannerRepository.save(callPlanner);
    }

    @Transactional
    public void updateCallPlannerForLead(Lead lead, LocalDateTime date) {

        CallPlanner callPlanner = callPlannerRepository.findByLeadId(lead.getId())
                .orElseThrow(() -> new RuntimeException("Call Planner not found for lead: " + lead.getId()));

        LocalDate lastCallDateInDb = callPlanner.getLastCallDate();
        LocalDate newLastCallDate = LocalDate.from(date);
        // Update only if the new date is after the existing last call date
        if (lastCallDateInDb == null || newLastCallDate.isAfter(lastCallDateInDb)) {
            callPlanner.setLastCallDate(newLastCallDate);

            CallFrequency callFrequency = callPlanner.getCallFrequency();
            LocalDate nextCallDate = calculateNextCallDate(newLastCallDate, callFrequency);

            callPlanner.setNextCallDate(nextCallDate);

            callPlannerRepository.save(callPlanner);
        } else {
            System.out.println("Provided date is not later than the existing last call date for lead: " + lead.getId());
        }
    }


    public List<Long> getLeadsRequiringCallToday() {
        LocalDate today = LocalDate.now();
        List<CallPlanner> callPlanners = callPlannerRepository.findByNextCallDate(today);
        List<LeadDTO> leadsToCallToday = new ArrayList<>();
        List<Long> leadIDs = new ArrayList<>();
        for (CallPlanner callPlanner : callPlanners) {
            leadIDs.add(callPlanner.getLead().getId());
        }
        return leadIDs;
    }

    @Transactional
    public CallPlannerDTO updateCallFrequency(Long leadId, CallFrequency callFrequency) {
        CallPlanner callPlanner = callPlannerRepository.findByLeadId(leadId)
                .orElseThrow(() -> new RuntimeException("Call planner not found for leadId: " + leadId));
        callPlanner.setCallFrequency(callFrequency);
        LocalDate nextCallDate = calculateNextCallDate(callPlanner.getLastCallDate(), callFrequency);

        // Next call date cannot be before today
        if (nextCallDate.isBefore(LocalDate.now())) {
            nextCallDate = LocalDate.now();
        }
        callPlanner.setNextCallDate(nextCallDate);

        callPlannerRepository.save(callPlanner);
        CallPlannerDTO callPlannerDTO = convertToDTO(callPlanner);
        return callPlannerDTO;
    }




    private LocalDate calculateNextCallDate(LocalDate lastCallDate, CallFrequency callFrequency) {
        LocalDate nextCallDate = LocalDate.now();
        switch (callFrequency.toString().toLowerCase()) {
            case "daily":
                nextCallDate = lastCallDate.plusDays(1);
                break;
            case "weekly":
                nextCallDate = lastCallDate.plusWeeks(1);
                break;
            case "monthly":
                nextCallDate = lastCallDate.plusMonths(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid call frequency");
        }
        return nextCallDate;
    }



    public CallPlannerDTO convertToDTO(CallPlanner callPlanner) {
        CallPlannerDTO callPlannerDTO = new CallPlannerDTO();
        callPlannerDTO.setLeadId(callPlanner.getLead().getId());
        callPlannerDTO.setCallFrequency(callPlanner.getCallFrequency());
        callPlannerDTO.setNextCallDate(callPlanner.getNextCallDate());
        callPlannerDTO.setLastCallDate(callPlanner.getLastCallDate());
        return callPlannerDTO;
    }
}

