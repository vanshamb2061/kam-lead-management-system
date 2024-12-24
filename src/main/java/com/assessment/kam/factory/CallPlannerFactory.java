package com.assessment.kam.factory;

import com.assessment.kam.model.CallPlanner;
import com.assessment.kam.model.Lead;

import java.time.LocalDate;

public class CallPlannerFactory {

    public static CallPlanner createCallPlanner(Lead lead, String callFrequency, LocalDate lastCallDate, LocalDate nextCallDate) {
        return new CallPlanner(lead, callFrequency, lastCallDate, nextCallDate);
    }
}
