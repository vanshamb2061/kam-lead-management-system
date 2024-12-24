package com.assessment.kam.dto;

import com.assessment.kam.enums.LeadStatus;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;

import java.util.List;

@Getter
@Setter
public class LeadDTO {
    @NotBlank(message = "Lead name is mandatory")
    private String name;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Lead status cannot be null")
    private LeadStatus status;
    private List<PointOfContactDTO> pointsOfContact;
}
