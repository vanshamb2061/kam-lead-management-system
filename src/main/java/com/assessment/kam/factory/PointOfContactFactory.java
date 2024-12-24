package com.assessment.kam.factory;

import com.assessment.kam.enums.POCRole;
import com.assessment.kam.model.PointOfContact;

public class PointOfContactFactory {
    public static PointOfContact createPointOfContact(String name, POCRole role, String email, String phone){
        return new PointOfContact(name, role, email, phone);
    }
}
