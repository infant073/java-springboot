package com.example.touristsafety.controller;

import com.example.touristsafety.entity.VendorBill;
import com.example.touristsafety.repository.VendorBillRepository;
import com.example.touristsafety.service.FinancialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-bills")
@CrossOrigin(origins = "*")
public class VendorBillController {

    @Autowired
    private VendorBillRepository vendorBillRepository;

    @Autowired
    private FinancialService financialService;

    @GetMapping
    public ResponseEntity<List<VendorBill>> getAllBills() {
        return ResponseEntity.ok(vendorBillRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<VendorBill> createVendorBill(@Valid @RequestBody VendorBill bill) {
        VendorBill created = financialService.createVendorBill(bill);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deleteVendorBill(@PathVariable Long id) {
        VendorBill bill = vendorBillRepository.findById(id)
                .orElseThrow(() -> new com.example.touristsafety.exception.ResourceNotFoundException("Vendor bill not found with id: " + id));
        vendorBillRepository.delete(bill);
        java.util.Map<String, String> res = new java.util.HashMap<>();
        res.put("message", "Vendor bill deleted successfully");
        return ResponseEntity.ok(res);
    }
}
