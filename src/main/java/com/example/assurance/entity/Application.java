package com.example.assurance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "application")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String CoverLetter;
    private String resume;
    private Date applicationDate;
    private Date interviewSchedule; // ex: "PENDING", "APPROVED", etc.

    // Relation N:1 avec JobOffer
    @ManyToOne
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

}
