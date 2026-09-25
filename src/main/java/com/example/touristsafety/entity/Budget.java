package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String budgetName;
    private String financialYear;
    private String region;
    private String zone;
    private Double allocatedAmount;
    private Double actualAmount;
    private String status; // ACTIVE, CLOSED

    public Budget() {}

    public Budget(String budgetName, String financialYear, String region, String zone, Double allocatedAmount, Double actualAmount, String status) {
        this.budgetName = budgetName;
        this.financialYear = financialYear;
        this.region = region;
        this.zone = zone;
        this.allocatedAmount = allocatedAmount != null ? allocatedAmount : 0.0;
        this.actualAmount = actualAmount != null ? actualAmount : 0.0;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBudgetName() { return budgetName; }
    public void setBudgetName(String budgetName) { this.budgetName = budgetName; }

    public String getFinancialYear() { return financialYear; }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public Double getAllocatedAmount() { return allocatedAmount; }
    public void setAllocatedAmount(Double allocatedAmount) { this.allocatedAmount = allocatedAmount; }

    public Double getActualAmount() { return actualAmount; }
    public void setActualAmount(Double actualAmount) { this.actualAmount = actualAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Transient
    public Double getRemainingBudget() {
        double alloc = allocatedAmount != null ? allocatedAmount : 0.0;
        double act = actualAmount != null ? actualAmount : 0.0;
        return alloc - act;
    }

    @Transient
    public Double getBudgetUtilization() {
        double alloc = allocatedAmount != null ? allocatedAmount : 0.0;
        double act = actualAmount != null ? actualAmount : 0.0;
        if (alloc == 0.0) return 0.0;
        return (act / alloc) * 100.0;
    }
}
