package com.assessment.kam.service;

import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.dto.PointOfContactDTO;
import com.assessment.kam.enums.CallFrequency;
import com.assessment.kam.factory.LeadFactory;
import com.assessment.kam.factory.PointOfContactFactory;
import com.assessment.kam.model.Lead;
import com.assessment.kam.enums.LeadStatus;
import com.assessment.kam.model.PointOfContact;
import com.assessment.kam.repository.LeadRepository;
import com.assessment.kam.repository.PointOfContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeadService {
    private final LeadRepository leadRepository;
    private final PointOfContactRepository pocRepository;

    @Autowired
    private CallPlannerService callPlannerService;

    @Autowired
    public LeadService(LeadRepository leadRepository, PointOfContactRepository pocRepository) {
        this.leadRepository = leadRepository;
        this.pocRepository = pocRepository;
    }

    @Transactional
    public LeadDTO createLead(String name, String address, LeadStatus status, List<PointOfContactDTO> pointOfContacts) {
        Lead lead = LeadFactory.createLead(name, address, status);

        List<PointOfContact> contacts = new ArrayList<>();
        for (PointOfContactDTO contactDTO : pointOfContacts) {
            PointOfContact contact = PointOfContactFactory.createPointOfContact(contactDTO.getName(),contactDTO.getRole(),contactDTO.getEmail(),contactDTO.getPhone());


            contact.setLead(lead);
            contacts.add(contact);
        }
        lead.setPointsOfContact(contacts);
        lead = leadRepository.save(lead);
        // Note: WEEKLY is default here, it can be updated using updateStatus endpoint
        callPlannerService.createCallPlannerForLead(lead, CallFrequency.WEEKLY);
        return convertToDTO(lead);
    }


    public LeadDTO getLeadDTOById(Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        if (lead.isPresent()) {
            return convertToDTO(lead.get());
        } else {
            throw new RuntimeException("Lead not found with id: " + id);
        }
    }

    public Optional<Lead> getLeadByID(Long id){
        return leadRepository.findById(id);
    }

    public LeadStatus getLeadStatus(Long leadID) {
        LeadDTO leadDTO = getLeadDTOById(leadID);
        return leadDTO.getStatus();
    }

    @Transactional
    public LeadDTO updateLeadStatus(Long leadID, LeadStatus leadStatus){
        Optional<Lead> leadOptional = leadRepository.findById(leadID);
        Lead lead = null;
        if(leadOptional.isPresent()){
            lead = leadOptional.get();
            lead.setStatus(leadStatus);
            leadRepository.save(lead);
        }
        return convertToDTO(lead);
    }


    public LeadDTO convertToDTO(Lead lead) {
        LeadDTO leadDTO = new LeadDTO();
        leadDTO.setId(lead.getId());
        leadDTO.setName(lead.getName());
        leadDTO.setAddress(lead.getAddress());
        leadDTO.setStatus(lead.getStatus());

        List<PointOfContactDTO> pocDTOs = lead.getPointsOfContact().stream()
                .map(poc -> new PointOfContactDTO(poc.getName(), poc.getRole(), poc.getEmail(), poc.getPhone()))
                .collect(Collectors.toList());

        leadDTO.setPointsOfContact(pocDTOs);

        return leadDTO;
    }
}
