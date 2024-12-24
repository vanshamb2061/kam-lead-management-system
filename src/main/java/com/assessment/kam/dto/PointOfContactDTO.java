package com.assessment.kam.dto;

import com.assessment.kam.enums.POCRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointOfContactDTO {
    private String name;
    private POCRole role;
    private String email;
    private String phone;

    public PointOfContactDTO(String name, POCRole role, String email, String phone){
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

}

