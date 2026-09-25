package com.example.touristsafety.controller;

import com.example.touristsafety.dto.SosRequestDto;
import com.example.touristsafety.entity.SosAlert;
import com.example.touristsafety.entity.Tourist;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.SosAlertRepository;
import com.example.touristsafety.repository.TouristRepository;
import com.example.touristsafety.service.SosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourists")
@CrossOrigin(origins = "*")
public class TouristController {

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private SosAlertRepository sosAlertRepository;

    @Autowired
    private SosService sosService;

    @GetMapping
    public ResponseEntity<List<Tourist>> getAllTourists() {
        return ResponseEntity.ok(touristRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable Long id) {
        Tourist tourist = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
        return ResponseEntity.ok(tourist);
    }

    @PostMapping
    public ResponseEntity<Tourist> createTourist(@Valid @RequestBody Tourist tourist) {
        if (tourist.getStatus() == null) tourist.setStatus("ACTIVE");
        if (tourist.getDigitalPassNumber() == null) tourist.getDigitalPassNumber();
        Tourist saved = touristRepository.save(tourist);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tourist> updateTourist(@PathVariable Long id, @Valid @RequestBody Tourist touristDetails) {
        Tourist existing = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));

        existing.setName(touristDetails.getName());
        existing.setEmail(touristDetails.getEmail());
        existing.setPhone(touristDetails.getPhone());
        existing.setAddress(touristDetails.getAddress());
        existing.setEmergencyContactName(touristDetails.getEmergencyContactName());
        existing.setEmergencyContactPhone(touristDetails.getEmergencyContactPhone());
        existing.setDigitalPassNumber(touristDetails.getDigitalPassNumber());
        existing.setPassType(touristDetails.getPassType());
        existing.setPassStartDate(touristDetails.getPassStartDate());
        existing.setPassEndDate(touristDetails.getPassEndDate());
        existing.setStatus(touristDetails.getStatus());

        Tourist updated = touristRepository.save(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourist(@PathVariable Long id) {
        Tourist existing = touristRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + id));
        touristRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{touristId}/sos")
    public ResponseEntity<SosAlert> triggerSos(@PathVariable Long touristId, @Valid @RequestBody SosRequestDto sosRequest) {
        SosAlert alert = sosService.triggerSos(touristId, sosRequest);
        return new ResponseEntity<>(alert, HttpStatus.CREATED);
    }

    @GetMapping("/active-sos")
    public ResponseEntity<List<SosAlert>> getActiveSosAlerts() {
        return ResponseEntity.ok(sosAlertRepository.findByStatus("ACTIVE"));
    }
}
