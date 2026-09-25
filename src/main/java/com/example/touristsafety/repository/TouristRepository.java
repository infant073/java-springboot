package com.example.touristsafety.repository;

import com.example.touristsafety.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Long> {
    Optional<Tourist> findByEmail(String email);
    List<Tourist> findByStatus(String status);
}
