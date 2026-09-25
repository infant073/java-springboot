package com.example.touristsafety.controller;

import com.example.touristsafety.entity.SatelliteTrackerLease;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.SatelliteTrackerLeaseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/satellite-leases")
@CrossOrigin(origins = "*")
public class SatelliteLeaseController {

    @Autowired
    private SatelliteTrackerLeaseRepository leaseRepository;

    @GetMapping
    public ResponseEntity<List<SatelliteTrackerLease>> getAllLeases() {
        return ResponseEntity.ok(leaseRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<SatelliteTrackerLease> createLease(@Valid @RequestBody SatelliteTrackerLease lease) {
        if (lease.getStatus() == null) lease.setStatus("ACTIVE");
        return new ResponseEntity<>(leaseRepository.save(lease), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SatelliteTrackerLease> updateLease(@PathVariable Long id, @Valid @RequestBody SatelliteTrackerLease details) {
        SatelliteTrackerLease existing = leaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lease not found with id: " + id));

        existing.setTrackerNumber(details.getTrackerNumber());
        existing.setLeaseStartDate(details.getLeaseStartDate());
        existing.setLeaseEndDate(details.getLeaseEndDate());
        existing.setLeaseAmount(details.getLeaseAmount());
        existing.setStatus(details.getStatus());

        return ResponseEntity.ok(leaseRepository.save(existing));
    }
}
