package com.assessment.kam.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InteractionDTO {
    @NotNull(message = "Lead ID cannot be null")
    private Long leadId;
    private String description;

    @NotNull(message = "Interaction date cannot be null")
    @PastOrPresent(message = "Interaction date must be in the past or present")
    private LocalDateTime date;
}
