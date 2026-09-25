package com.example.touristsafety.repository;

import com.example.touristsafety.entity.EmergencyResponseOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyResponseOperationRepository extends JpaRepository<EmergencyResponseOperation, Long> {
    List<EmergencyResponseOperation> findBySosAlertId(Long sosAlertId);
}
