package com.assessment.kam.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;
    private String description;
    private LocalDateTime date;

    public Interaction(){
    }

    public Interaction(Lead lead, String description, LocalDateTime date){
        this.lead = lead;
        this.description = description;
        this.date = date;
    }
}
