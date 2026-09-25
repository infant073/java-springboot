package com.example.touristsafety.controller;

import com.example.touristsafety.entity.EmergencyRescueService;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.EmergencyRescueServiceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rescue-services")
@CrossOrigin(origins = "*")
public class RescueServiceController {

    @Autowired
    private EmergencyRescueServiceRepository rescueServiceRepository;

    @GetMapping
    public ResponseEntity<List<EmergencyRescueService>> getAllServices() {
        return ResponseEntity.ok(rescueServiceRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<EmergencyRescueService> createService(@Valid @RequestBody EmergencyRescueService service) {
        if (service.getAvailabilityStatus() == null) service.setAvailabilityStatus("AVAILABLE");
        return new ResponseEntity<>(rescueServiceRepository.save(service), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmergencyRescueService> updateService(@PathVariable Long id, @Valid @RequestBody EmergencyRescueService details) {
        EmergencyRescueService existing = rescueServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rescue Service not found with id: " + id));

        existing.setServiceName(details.getServiceName());
        existing.setServiceProvider(details.getServiceProvider());
        existing.setContactNumber(details.getContactNumber());
        existing.setServiceArea(details.getServiceArea());
        existing.setCost(details.getCost());
        existing.setAvailabilityStatus(details.getAvailabilityStatus());

        return ResponseEntity.ok(rescueServiceRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        EmergencyRescueService existing = rescueServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rescue Service not found with id: " + id));
        rescueServiceRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }
}
