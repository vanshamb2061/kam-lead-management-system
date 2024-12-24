package com.assessment.kam.controller;

import com.assessment.kam.dto.InteractionDTO;
import com.assessment.kam.model.Interaction;
import com.assessment.kam.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    // Endpoint to create a new interaction
    @PostMapping("/create")
    public ResponseEntity<Interaction> createInteraction(@RequestBody InteractionDTO interactionDTO) {
        Interaction interaction = interactionService.createInteraction(interactionDTO);
        return ResponseEntity.ok(interaction);
    }

    // Endpoint to get all interactions for a specific lead
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<Interaction>> getInteractionsByLead(@PathVariable Long leadId) {
        List<Interaction> interactions = interactionService.getInteractionsByLead(leadId);
        return ResponseEntity.ok(interactions);
    }


}
