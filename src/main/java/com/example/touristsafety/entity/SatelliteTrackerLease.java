package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "satellite_tracker_leases")
public class SatelliteTrackerLease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long providerId;
    private String trackerNumber;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;
    private Double leaseAmount;
    private String status; // ACTIVE, EXPIRED, TERMINATED

    public SatelliteTrackerLease() {}

    public SatelliteTrackerLease(Long providerId, String trackerNumber, LocalDate leaseStartDate, LocalDate leaseEndDate, Double leaseAmount, String status) {
        this.providerId = providerId;
        this.trackerNumber = trackerNumber;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.leaseAmount = leaseAmount;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }

    public String getTrackerNumber() { return trackerNumber; }
    public void setTrackerNumber(String trackerNumber) { this.trackerNumber = trackerNumber; }

    public LocalDate getLeaseStartDate() { return leaseStartDate; }
    public void setLeaseStartDate(LocalDate leaseStartDate) { this.leaseStartDate = leaseStartDate; }

    public LocalDate getLeaseEndDate() { return leaseEndDate; }
    public void setLeaseEndDate(LocalDate leaseEndDate) { this.leaseEndDate = leaseEndDate; }

    public Double getLeaseAmount() { return leaseAmount; }
    public void setLeaseAmount(Double leaseAmount) { this.leaseAmount = leaseAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
