package com.assessment.kam.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InteractionDTO {
    private Long leadId;
    private String description;
    private LocalDateTime date;
}
