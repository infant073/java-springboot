package com.example.touristsafety.repository;

import com.example.touristsafety.entity.SatelliteTrackerLease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SatelliteTrackerLeaseRepository extends JpaRepository<SatelliteTrackerLease, Long> {
}
