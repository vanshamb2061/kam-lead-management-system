package com.assessment.kam.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@Getter
public class CallPlanner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    private String callFrequency;
    private LocalDate lastCallDate;
    private LocalDate nextCallDate;

    public CallPlanner(){
    }

    public CallPlanner(Lead lead, String callFrequency, LocalDate lastCallDate, LocalDate nextCallDate){
        this.lead = lead;
        this.callFrequency = callFrequency;
        this.lastCallDate = lastCallDate;
        this.nextCallDate = nextCallDate;
    }

    public void setLastCallDate(LocalDate lastCallDate) {
        this.lastCallDate = lastCallDate;
    }

    public void setNextCallDate(LocalDate nextCallDate) {
        this.nextCallDate = nextCallDate;
    }

    public void setCallFrequency(String callFrequency) {
        this.callFrequency = callFrequency;
    }
}
