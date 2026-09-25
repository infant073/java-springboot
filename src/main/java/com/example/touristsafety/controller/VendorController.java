package com.example.touristsafety.controller;

import com.example.touristsafety.entity.RescueGearVendor;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.RescueGearVendorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "*")
public class VendorController {

    @Autowired
    private RescueGearVendorRepository vendorRepository;

    @GetMapping
    public ResponseEntity<List<RescueGearVendor>> getAllVendors() {
        return ResponseEntity.ok(vendorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RescueGearVendor> getVendorById(@PathVariable Long id) {
        RescueGearVendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        return ResponseEntity.ok(vendor);
    }

    @PostMapping
    public ResponseEntity<RescueGearVendor> createVendor(@Valid @RequestBody RescueGearVendor vendor) {
        if (vendor.getStatus() == null) vendor.setStatus("ACTIVE");
        RescueGearVendor saved = vendorRepository.save(vendor);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RescueGearVendor> updateVendor(@PathVariable Long id, @Valid @RequestBody RescueGearVendor details) {
        RescueGearVendor existing = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));

        existing.setVendorName(details.getVendorName());
        existing.setContactPerson(details.getContactPerson());
        existing.setEmail(details.getEmail());
        existing.setPhone(details.getPhone());
        existing.setAddress(details.getAddress());
        existing.setGstNumber(details.getGstNumber());
        existing.setStatus(details.getStatus());

        return ResponseEntity.ok(vendorRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        RescueGearVendor existing = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        vendorRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }
}
