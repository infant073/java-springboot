package com.example.touristsafety.repository;

import com.example.touristsafety.entity.DigitalTouristPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalTouristPassRepository extends JpaRepository<DigitalTouristPass, Long> {
    List<DigitalTouristPass> findByTouristId(Long touristId);
    List<DigitalTouristPass> findByStatus(String status);
}
