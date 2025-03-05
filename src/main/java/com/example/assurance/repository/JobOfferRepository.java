package com.example.assurance.repository;

import com.example.assurance.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    // Méthodes personnalisées si besoin
}
