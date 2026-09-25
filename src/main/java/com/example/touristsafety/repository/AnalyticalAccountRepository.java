package com.example.touristsafety.repository;

import com.example.touristsafety.entity.AnalyticalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticalAccountRepository extends JpaRepository<AnalyticalAccount, Long> {
}
