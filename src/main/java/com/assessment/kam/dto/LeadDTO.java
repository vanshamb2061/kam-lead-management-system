package com.assessment.kam.dto;

import com.assessment.kam.enums.LeadStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LeadDTO {
    private String name;
    private String address;
    private LeadStatus status;
    private List<PointOfContactDTO> pointsOfContact;
}
