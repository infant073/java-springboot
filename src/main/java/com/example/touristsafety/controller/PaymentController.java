package com.example.touristsafety.controller;

import com.example.touristsafety.entity.Payment;
import com.example.touristsafety.repository.PaymentRepository;
import com.example.touristsafety.service.FinancialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FinancialService financialService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody Payment payment) {
        Payment created = financialService.processPayment(payment);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deletePayment(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new com.example.touristsafety.exception.ResourceNotFoundException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
        java.util.Map<String, String> res = new java.util.HashMap<>();
        res.put("message", "Payment deleted successfully");
        return ResponseEntity.ok(res);
    }
}
