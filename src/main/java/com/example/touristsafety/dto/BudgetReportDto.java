package com.example.touristsafety.dto;

import com.example.touristsafety.entity.Budget;
import java.util.List;

public class BudgetReportDto {

    private List<Budget> budgets;
    private Double totalAllocated;
    private Double totalActual;
    private Double totalRemaining;
    private Double overallUtilizationPercentage;

    public BudgetReportDto() {}

    public BudgetReportDto(List<Budget> budgets, Double totalAllocated, Double totalActual, Double totalRemaining, Double overallUtilizationPercentage) {
        this.budgets = budgets;
        this.totalAllocated = totalAllocated;
        this.totalActual = totalActual;
        this.totalRemaining = totalRemaining;
        this.overallUtilizationPercentage = overallUtilizationPercentage;
    }

    public List<Budget> getBudgets() { return budgets; }
    public void setBudgets(List<Budget> budgets) { this.budgets = budgets; }

    public Double getTotalAllocated() { return totalAllocated; }
    public void setTotalAllocated(Double totalAllocated) { this.totalAllocated = totalAllocated; }

    public Double getTotalActual() { return totalActual; }
    public void setTotalActual(Double totalActual) { this.totalActual = totalActual; }

    public Double getTotalRemaining() { return totalRemaining; }
    public void setTotalRemaining(Double totalRemaining) { this.totalRemaining = totalRemaining; }

    public Double getOverallUtilizationPercentage() { return overallUtilizationPercentage; }
    public void setOverallUtilizationPercentage(Double overallUtilizationPercentage) { this.overallUtilizationPercentage = overallUtilizationPercentage; }
}
