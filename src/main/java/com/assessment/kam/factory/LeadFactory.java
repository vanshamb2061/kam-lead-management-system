package com.assessment.kam.factory;

import com.assessment.kam.model.Lead;
import com.assessment.kam.enums.LeadStatus;

public class LeadFactory {
    public static Lead createLead(String name, String address, LeadStatus status) {
        return new Lead(name, address, status);
    }
}