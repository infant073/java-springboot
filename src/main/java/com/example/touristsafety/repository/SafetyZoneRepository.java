package com.example.touristsafety.repository;

import com.example.touristsafety.entity.SafetyZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SafetyZoneRepository extends JpaRepository<SafetyZone, Long> {
    List<SafetyZone> findByStatus(String status);
}
