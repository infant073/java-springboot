package com.example.touristsafety.controller;

import com.example.touristsafety.entity.TelecomProvider;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.TelecomProviderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telecom-providers")
@CrossOrigin(origins = "*")
public class TelecomController {

    @Autowired
    private TelecomProviderRepository telecomRepository;

    @GetMapping
    public ResponseEntity<List<TelecomProvider>> getAllProviders() {
        return ResponseEntity.ok(telecomRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<TelecomProvider> createProvider(@Valid @RequestBody TelecomProvider provider) {
        if (provider.getStatus() == null) provider.setStatus("ACTIVE");
        return new ResponseEntity<>(telecomRepository.save(provider), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TelecomProvider> updateProvider(@PathVariable Long id, @Valid @RequestBody TelecomProvider details) {
        TelecomProvider existing = telecomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telecom Provider not found with id: " + id));

        existing.setProviderName(details.getProviderName());
        existing.setContactPerson(details.getContactPerson());
        existing.setPhone(details.getPhone());
        existing.setEmail(details.getEmail());
        existing.setServiceType(details.getServiceType());
        existing.setStatus(details.getStatus());

        return ResponseEntity.ok(telecomRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        TelecomProvider existing = telecomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telecom Provider not found with id: " + id));
        telecomRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }
}
