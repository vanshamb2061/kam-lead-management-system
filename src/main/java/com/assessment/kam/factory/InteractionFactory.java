package com.assessment.kam.factory;

import com.assessment.kam.model.CallPlanner;
import com.assessment.kam.model.Interaction;
import com.assessment.kam.model.Lead;

import java.time.LocalDateTime;

public class InteractionFactory {
    public static Interaction createInteraction(Lead lead, String description, LocalDateTime date){
        return new Interaction(lead, description, date);
    }
}
