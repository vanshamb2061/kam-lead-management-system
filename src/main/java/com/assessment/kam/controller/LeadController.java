package com.assessment.kam.controller;

import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.enums.LeadStatus;
import com.assessment.kam.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/leads")
class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping()
    public ResponseEntity<LeadDTO> createLead(@RequestBody LeadDTO leadDTO) {
        LeadDTO newLeadDTO = leadService.createLead(leadDTO.getName(), leadDTO.getAddress(), leadDTO.getStatus(), leadDTO.getPointsOfContact());
        return ResponseEntity.ok(newLeadDTO);
    }

    @GetMapping("{id}/status")
    public ResponseEntity<LeadStatus> getLeadStatus(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getLeadStatus(id));
    }

    @PutMapping("{id}/update-status")
    public ResponseEntity<LeadDTO> updateLeadStatus(@PathVariable Long id, @RequestParam LeadStatus newStatus) {
        return ResponseEntity.ok(leadService.updateLeadStatus(id, newStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadDTO> getLeadById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(leadService.getLeadDTOById(id));
    }
}
