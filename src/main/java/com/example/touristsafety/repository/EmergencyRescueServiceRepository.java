package com.example.touristsafety.repository;

import com.example.touristsafety.entity.EmergencyRescueService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyRescueServiceRepository extends JpaRepository<EmergencyRescueService, Long> {
}
