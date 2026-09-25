package com.example.touristsafety.controller;

import com.example.touristsafety.entity.PurchaseOrder;
import com.example.touristsafety.repository.PurchaseOrderRepository;
import com.example.touristsafety.service.FinancialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private FinancialService financialService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@Valid @RequestBody PurchaseOrder po) {
        PurchaseOrder created = financialService.createPurchaseOrder(po);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deletePurchaseOrder(@PathVariable Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new com.example.touristsafety.exception.ResourceNotFoundException("PO not found with id: " + id));
        purchaseOrderRepository.delete(po);
        java.util.Map<String, String> res = new java.util.HashMap<>();
        res.put("message", "Purchase order deleted successfully");
        return ResponseEntity.ok(res);
    }
}
