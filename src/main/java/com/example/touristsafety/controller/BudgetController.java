package com.example.touristsafety.controller;

import com.example.touristsafety.entity.Budget;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.BudgetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.ok(budgetRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@Valid @RequestBody Budget budget) {
        if (budget.getStatus() == null) budget.setStatus("ACTIVE");
        if (budget.getActualAmount() == null) budget.setActualAmount(0.0);
        return new ResponseEntity<>(budgetRepository.save(budget), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long id, @Valid @RequestBody Budget details) {
        Budget existing = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));

        existing.setBudgetName(details.getBudgetName());
        existing.setFinancialYear(details.getFinancialYear());
        existing.setRegion(details.getRegion());
        existing.setZone(details.getZone());
        existing.setAllocatedAmount(details.getAllocatedAmount());
        if (details.getActualAmount() != null) {
            existing.setActualAmount(details.getActualAmount());
        }
        existing.setStatus(details.getStatus());

        return ResponseEntity.ok(budgetRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<java.util.Map<String, String>> deleteBudget(@PathVariable Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        budgetRepository.delete(budget);
        java.util.Map<String, String> res = new java.util.HashMap<>();
        res.put("message", "Budget deleted successfully");
        return ResponseEntity.ok(res);
    }
}
