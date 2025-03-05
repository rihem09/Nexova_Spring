package com.example.assurance.repository;

import com.example.assurance.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    // Méthodes personnalisées si besoin
}
