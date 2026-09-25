package com.example.touristsafety.repository;

import com.example.touristsafety.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTransactionTypeAndTransactionId(String transactionType, Long transactionId);
}
