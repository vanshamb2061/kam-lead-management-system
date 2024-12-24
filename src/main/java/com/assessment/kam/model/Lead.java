package com.assessment.kam.model;

import com.assessment.kam.enums.LeadStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PointOfContact> pointsOfContact = new ArrayList<>();

    public Lead(){
    }
    public Lead(String name, String address, LeadStatus status, List<PointOfContact> pointsOfContact){
        this.name = name;
        this.address = address;
        this.status = status;
        this.pointsOfContact = pointsOfContact;
    }

    public Lead(String name, String address, LeadStatus status){
        this.name = name;
        this.address = address;
        this.status = status;
        this.pointsOfContact = new ArrayList<>();
    }

    public void setPointsOfContact(List<PointOfContact> pointsOfContact) {
        this.pointsOfContact = pointsOfContact;
    }
}