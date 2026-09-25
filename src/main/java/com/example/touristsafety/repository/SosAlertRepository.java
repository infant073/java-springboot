package com.example.touristsafety.repository;

import com.example.touristsafety.entity.SosAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SosAlertRepository extends JpaRepository<SosAlert, Long> {
    List<SosAlert> findByStatus(String status);
    List<SosAlert> findByTouristId(Long touristId);
}
