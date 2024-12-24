package com.assessment.kam.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CallPlannerDTO {
    private Long leadId;
    private String callFrequency;
    private LocalDate lastCallDate;
    private LocalDate nextCallDate;
}

