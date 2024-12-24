package com.assessment.kam.model;

import com.assessment.kam.enums.CallFrequency;
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

    private CallFrequency callFrequency;
    private LocalDate lastCallDate;
    private LocalDate nextCallDate;

    public CallPlanner(){
    }

    public CallPlanner(Lead lead, CallFrequency callFrequency, LocalDate lastCallDate, LocalDate nextCallDate){
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

    public void setCallFrequency(CallFrequency callFrequency) {
        this.callFrequency = callFrequency;
    }
}
