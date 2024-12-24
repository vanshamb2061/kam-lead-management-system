package com.assessment.kam.model;

import com.assessment.kam.enums.POCRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PointOfContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private POCRole role;
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "lead_id")
    @JsonBackReference
    private Lead lead;

    public PointOfContact(){
    }

    public PointOfContact(String name, POCRole role, String email, String phone){
        this.name = name;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }
}