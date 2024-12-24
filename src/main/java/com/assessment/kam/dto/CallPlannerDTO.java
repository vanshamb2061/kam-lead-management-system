package com.assessment.kam.dto;

import com.assessment.kam.enums.CallFrequency;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CallPlannerDTO {
    @NotNull(message = "Lead ID cannot be null")
    private Long leadId;

    @NotNull(message = "Call frequency is mandatory")
    private CallFrequency callFrequency;

    @NotNull(message = "Last call date cannot be null")
    @PastOrPresent(message = "Last call date must be in the past or present")
    private LocalDate lastCallDate;

    @NotNull(message = "Next call date cannot be null")
    @FutureOrPresent(message = "Next call date must be in the future or present")
    private LocalDate nextCallDate;
}

