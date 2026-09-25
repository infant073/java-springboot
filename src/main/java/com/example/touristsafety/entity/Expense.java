package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expenseType; // RESCUE_OPERATION, EQUIPMENT_MAINTENANCE, TELECOM_LEASE
    private String description;
    private Double amount;
    private LocalDate expenseDate;
    private String zone;
    private Long responseOperationId;

    public Expense() {}

    public Expense(String expenseType, String description, Double amount, LocalDate expenseDate, String zone, Long responseOperationId) {
        this.expenseType = expenseType;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.zone = zone;
        this.responseOperationId = responseOperationId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExpenseType() { return expenseType; }
    public void setExpenseType(String expenseType) { this.expenseType = expenseType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public Long getResponseOperationId() { return responseOperationId; }
    public void setResponseOperationId(Long responseOperationId) { this.responseOperationId = responseOperationId; }
}
