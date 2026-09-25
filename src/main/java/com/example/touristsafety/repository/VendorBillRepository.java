package com.example.touristsafety.repository;

import com.example.touristsafety.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorBillRepository extends JpaRepository<VendorBill, Long> {
    List<VendorBill> findByPurchaseOrderId(Long purchaseOrderId);
}
