package com.example.touristsafety.repository;

import com.example.touristsafety.entity.TelecomProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelecomProviderRepository extends JpaRepository<TelecomProvider, Long> {
}
