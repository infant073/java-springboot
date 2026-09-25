package com.example.touristsafety.controller;

import com.example.touristsafety.entity.EmergencyResponseOperation;
import com.example.touristsafety.entity.Expense;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.EmergencyResponseOperationRepository;
import com.example.touristsafety.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rescue-operations")
@CrossOrigin(origins = "*")
public class RescueOperationController {

    @Autowired
    private EmergencyResponseOperationRepository responseOperationRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping
    public ResponseEntity<List<EmergencyResponseOperation>> getAllOperations() {
        return ResponseEntity.ok(responseOperationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<EmergencyResponseOperation> createOperation(@Valid @RequestBody EmergencyResponseOperation op) {
        if (op.getDispatchTime() == null) op.setDispatchTime(LocalDateTime.now());
        if (op.getStatus() == null) op.setStatus("DISPATCHED");
        
        EmergencyResponseOperation saved = responseOperationRepository.save(op);

        // Record as operational expense
        expenseRepository.save(new Expense(
                "RESCUE_OPERATION",
                "Emergency dispatch operation cost for SOS #" + saved.getSosAlertId(),
                saved.getTotalCost(),
                LocalDate.now(),
                saved.getLocation(),
                saved.getId()
        ));

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmergencyResponseOperation> updateOperation(@PathVariable Long id, @Valid @RequestBody EmergencyResponseOperation details) {
        EmergencyResponseOperation existing = responseOperationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rescue operation not found with id: " + id));

        existing.setResponseTeam(details.getResponseTeam());
        existing.setArrivalTime(details.getArrivalTime());
        existing.setCompletionTime(details.getCompletionTime());
        existing.setLocation(details.getLocation());
        existing.setRescueDescription(details.getRescueDescription());
        existing.setFuelCost(details.getFuelCost());
        existing.setEquipmentCost(details.getEquipmentCost());
        existing.setServiceCost(details.getServiceCost());
        existing.setStatus(details.getStatus());

        EmergencyResponseOperation updated = responseOperationRepository.save(existing);

        // Update corresponding expense
        expenseRepository.save(new Expense(
                "RESCUE_OPERATION",
                "Updated rescue operation cost for SOS #" + updated.getSosAlertId(),
                updated.getTotalCost(),
                LocalDate.now(),
                updated.getLocation(),
                updated.getId()
        ));

        return ResponseEntity.ok(updated);
    }
}
