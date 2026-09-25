package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_response_operations")
public class EmergencyResponseOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sosAlertId;
    private String responseTeam;
    private LocalDateTime dispatchTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime completionTime;
    private String location;
    @Column(length = 1000)
    private String rescueDescription;
    private Double fuelCost;
    private Double equipmentCost;
    private Double serviceCost;
    private Double totalCost;
    private String status; // DISPATCHED, IN_PROGRESS, COMPLETED, CANCELLED

    public EmergencyResponseOperation() {}

    public EmergencyResponseOperation(Long sosAlertId, String responseTeam, LocalDateTime dispatchTime, String location, String rescueDescription, Double fuelCost, Double equipmentCost, Double serviceCost, String status) {
        this.sosAlertId = sosAlertId;
        this.responseTeam = responseTeam;
        this.dispatchTime = dispatchTime;
        this.location = location;
        this.rescueDescription = rescueDescription;
        this.fuelCost = fuelCost != null ? fuelCost : 0.0;
        this.equipmentCost = equipmentCost != null ? equipmentCost : 0.0;
        this.serviceCost = serviceCost != null ? serviceCost : 0.0;
        this.totalCost = this.fuelCost + this.equipmentCost + this.serviceCost;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSosAlertId() { return sosAlertId; }
    public void setSosAlertId(Long sosAlertId) { this.sosAlertId = sosAlertId; }

    public String getResponseTeam() { return responseTeam; }
    public void setResponseTeam(String responseTeam) { this.responseTeam = responseTeam; }

    public LocalDateTime getDispatchTime() { return dispatchTime; }
    public void setDispatchTime(LocalDateTime dispatchTime) { this.dispatchTime = dispatchTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public LocalDateTime getCompletionTime() { return completionTime; }
    public void setCompletionTime(LocalDateTime completionTime) { this.completionTime = completionTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getRescueDescription() { return rescueDescription; }
    public void setRescueDescription(String rescueDescription) { this.rescueDescription = rescueDescription; }

    public Double getFuelCost() { return fuelCost; }
    public void setFuelCost(Double fuelCost) { this.fuelCost = fuelCost; }

    public Double getEquipmentCost() { return equipmentCost; }
    public void setEquipmentCost(Double equipmentCost) { this.equipmentCost = equipmentCost; }

    public Double getServiceCost() { return serviceCost; }
    public void setServiceCost(Double serviceCost) { this.serviceCost = serviceCost; }

    public Double getTotalCost() { return totalCost; }
    public void setTotalCost(Double totalCost) { this.totalCost = totalCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @PrePersist
    @PreUpdate
    public void calculateTotalCost() {
        double f = fuelCost != null ? fuelCost : 0.0;
        double e = equipmentCost != null ? equipmentCost : 0.0;
        double s = serviceCost != null ? serviceCost : 0.0;
        this.totalCost = f + e + s;
    }
}
