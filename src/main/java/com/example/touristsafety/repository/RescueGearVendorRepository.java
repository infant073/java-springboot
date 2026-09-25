package com.example.touristsafety.repository;

import com.example.touristsafety.entity.RescueGearVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RescueGearVendorRepository extends JpaRepository<RescueGearVendor, Long> {
}
