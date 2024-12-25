package com.assessment.kam.service;

import com.assessment.kam.dto.InteractionDTO;
import com.assessment.kam.factory.InteractionFactory;
import com.assessment.kam.model.CallPlanner;
import com.assessment.kam.model.Interaction;
import com.assessment.kam.model.Lead;
import com.assessment.kam.repository.InteractionRepository;
import com.assessment.kam.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InteractionService {
    @Autowired
    private CallPlannerService callPlannerService;

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private LeadRepository leadRepository;

    @Transactional
    public InteractionDTO createInteraction(InteractionDTO interactionDTO) {
        Lead lead = leadRepository.findById(interactionDTO.getLeadId())
                .orElseThrow(() -> new RuntimeException("Lead not found"));
        Interaction interaction = InteractionFactory.createInteraction(lead,interactionDTO.getDescription(),interactionDTO.getDate() != null ? interactionDTO.getDate() : LocalDateTime.now());
        //TODO: Add validation to ensure lead and POC are from same restaurant
        interaction = interactionRepository.save(interaction);
        callPlannerService.updateCallPlannerForLead(lead, interactionDTO.getDate());
        return convertToDTO(interaction);
    }

    public List<InteractionDTO> getInteractionsByLead(Long leadId) {
        List<Interaction> interactions = interactionRepository.findByLeadId(leadId);
        return interactions.stream().map(this::convertToDTO).toList();
    }

    public InteractionDTO convertToDTO(Interaction interaction){
        InteractionDTO interactionDTO = new InteractionDTO(interaction.getLead().getId(),interaction.getDescription(),interaction.getDate());
        return interactionDTO;
    }
}
