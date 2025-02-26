package com.example.event.Repositories;

import com.example.event.Entities.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepo extends JpaRepository<Pass, Long> {
}
