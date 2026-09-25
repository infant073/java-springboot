package com.example.touristsafety.controller;

import com.example.touristsafety.entity.SosAlert;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.SosAlertRepository;
import com.example.touristsafety.service.SosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin(origins = "*")
public class SosAlertController {

    @Autowired
    private SosAlertRepository sosAlertRepository;

    @Autowired
    private SosService sosService;

    @GetMapping
    public ResponseEntity<List<SosAlert>> getAllSosAlerts() {
        return ResponseEntity.ok(sosAlertRepository.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<SosAlert>> getActiveSosAlerts() {
        return ResponseEntity.ok(sosAlertRepository.findByStatus("ACTIVE"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SosAlert> getSosAlertById(@PathVariable Long id) {
        SosAlert alert = sosAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SOS Alert not found with id: " + id));
        return ResponseEntity.ok(alert);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SosAlert> updateSosStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null || status.isBlank()) {
            status = "RESOLVED";
        }
        SosAlert updated = sosService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deleteSosAlert(@PathVariable Long id) {
        SosAlert alert = sosAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SOS Alert not found with id: " + id));
        sosAlertRepository.delete(alert);
        java.util.Map<String, String> res = new java.util.HashMap<>();
        res.put("message", "SOS alert deleted successfully");
        return ResponseEntity.ok(res);
    }
}
