package com.example.touristsafety.controller;

import com.example.touristsafety.dto.GeoCheckResultDto;
import com.example.touristsafety.entity.SafetyZone;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.SafetyZoneRepository;
import com.example.touristsafety.service.GeoFencingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/safety-zones")
@CrossOrigin(origins = "*")
public class SafetyZoneController {

    @Autowired
    private SafetyZoneRepository safetyZoneRepository;

    @Autowired
    private GeoFencingService geoFencingService;

    @GetMapping
    public ResponseEntity<List<SafetyZone>> getAllZones() {
        return ResponseEntity.ok(safetyZoneRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SafetyZone> getZoneById(@PathVariable Long id) {
        SafetyZone zone = safetyZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Safety Zone not found with id: " + id));
        return ResponseEntity.ok(zone);
    }

    @PostMapping
    public ResponseEntity<SafetyZone> createZone(@Valid @RequestBody SafetyZone zone) {
        if (zone.getStatus() == null) zone.setStatus("ACTIVE");
        SafetyZone saved = safetyZoneRepository.save(zone);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SafetyZone> updateZone(@PathVariable Long id, @Valid @RequestBody SafetyZone details) {
        SafetyZone existing = safetyZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Safety Zone not found with id: " + id));

        existing.setZoneName(details.getZoneName());
        existing.setDescription(details.getDescription());
        existing.setLatitude(details.getLatitude());
        existing.setLongitude(details.getLongitude());
        existing.setRadius(details.getRadius());
        existing.setDangerLevel(details.getDangerLevel());
        existing.setWarningMessage(details.getWarningMessage());
        existing.setStatus(details.getStatus());

        return ResponseEntity.ok(safetyZoneRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        SafetyZone existing = safetyZoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Safety Zone not found with id: " + id));
        safetyZoneRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    public ResponseEntity<GeoCheckResultDto> checkLocation(@RequestParam double latitude, @RequestParam double longitude) {
        GeoCheckResultDto result = geoFencingService.checkCoordinates(latitude, longitude);
        return ResponseEntity.ok(result);
    }
}
