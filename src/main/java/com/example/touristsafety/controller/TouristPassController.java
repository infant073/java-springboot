package com.example.touristsafety.controller;

import com.example.touristsafety.entity.DigitalTouristPass;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.DigitalTouristPassRepository;
import com.example.touristsafety.service.FinancialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class TouristPassController {

    @Autowired
    private DigitalTouristPassRepository passRepository;

    @Autowired
    private FinancialService financialService;

    @GetMapping("/api/tourist-passes")
    public ResponseEntity<List<DigitalTouristPass>> getAllPasses() {
        return ResponseEntity.ok(passRepository.findAll());
    }

    @GetMapping("/api/tourist-passes/{id}")
    public ResponseEntity<DigitalTouristPass> getPassById(@PathVariable Long id) {
        DigitalTouristPass pass = passRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist Pass not found with id: " + id));
        return ResponseEntity.ok(pass);
    }

    @PostMapping("/api/tourist-passes")
    public ResponseEntity<DigitalTouristPass> createPass(@Valid @RequestBody DigitalTouristPass pass) {
        DigitalTouristPass createdPass = financialService.createTouristPass(pass);
        return new ResponseEntity<>(createdPass, HttpStatus.CREATED);
    }

    @PutMapping("/api/tourist-passes/{id}")
    public ResponseEntity<DigitalTouristPass> updatePass(@PathVariable Long id, @Valid @RequestBody DigitalTouristPass details) {
        DigitalTouristPass existing = passRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist Pass not found with id: " + id));

        existing.setPassType(details.getPassType());
        existing.setExpiryDate(details.getExpiryDate());
        existing.setAmount(details.getAmount());
        existing.setPaymentStatus(details.getPaymentStatus());
        existing.setStatus(details.getStatus());

        return ResponseEntity.ok(passRepository.save(existing));
    }

    @PostMapping("/api/invoices")
    public ResponseEntity<Map<String, Object>> generateInvoice(@RequestBody Map<String, Object> request) {
        Long passId = request.get("passId") != null ? Long.parseLong(request.get("passId").toString()) : 1L;
        DigitalTouristPass pass = passRepository.findById(passId)
                .orElseThrow(() -> new ResourceNotFoundException("Pass not found with id: " + passId));

        Map<String, Object> invoice = new HashMap<>();
        invoice.put("invoiceNumber", "INV-" + pass.getPassNumber());
        invoice.put("issueDate", pass.getIssueDate());
        invoice.put("touristId", pass.getTouristId());
        invoice.put("passNumber", pass.getPassNumber());
        invoice.put("passType", pass.getPassType());
        invoice.put("amount", pass.getAmount());
        invoice.put("taxAmount", pass.getAmount() * 0.18);
        invoice.put("totalAmount", pass.getAmount() * 1.18);
        invoice.put("paymentStatus", pass.getPaymentStatus());

        return ResponseEntity.ok(invoice);
    }

    @DeleteMapping("/api/tourist-passes/{id}")
    public ResponseEntity<Map<String, String>> deletePass(@PathVariable Long id) {
        DigitalTouristPass pass = passRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist Pass not found with id: " + id));
        passRepository.delete(pass);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tourist pass deleted successfully");
        return ResponseEntity.ok(response);
    }
}
