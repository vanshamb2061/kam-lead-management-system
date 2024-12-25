package com.assessment.kam.controller;

import com.assessment.kam.dto.InteractionDTO;
import com.assessment.kam.model.Interaction;
import com.assessment.kam.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    // Endpoint to create a new interaction
    @PostMapping("/create")
    public ResponseEntity<InteractionDTO> createInteraction(@RequestBody InteractionDTO interactionDTO, @RequestParam String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);
        LocalDateTime utcDate = interactionDTO.getDate().atZone(zoneId).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        interactionDTO.setDate(utcDate);
        interactionDTO = interactionService.createInteraction(interactionDTO);
        return ResponseEntity.ok(interactionDTO);
    }

    // Endpoint to get all interactions for a specific lead
    @GetMapping("/lead/{leadId}")
    public ResponseEntity<List<InteractionDTO>> getInteractionsByLead(@PathVariable Long leadId, @RequestParam String timezone) {
        List<InteractionDTO> interactionDTOList = interactionService.getInteractionsByLead(leadId);
        ZoneId zoneId = ZoneId.of(timezone);
        interactionDTOList = interactionDTOList.stream()
                .map(interactionDTO -> {
                    interactionDTO.setDate(interactionDTO.getDate()
                            .atZone(ZoneId.of("UTC"))
                            .withZoneSameInstant(zoneId)
                            .toLocalDateTime());
                    return interactionDTO;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(interactionDTOList);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String errorMessage = "Missing required parameter: " + ex.getParameterName();
        return ResponseEntity.badRequest().body(errorMessage);
    }


}
