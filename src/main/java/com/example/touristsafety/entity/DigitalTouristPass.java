package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "digital_tourist_passes")
public class DigitalTouristPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long touristId;
    private String passNumber;
    private String passType;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private Double amount;
    private String paymentStatus; // PAID, UNPAID, PENDING
    private String status; // ACTIVE, EXPIRED, CANCELLED

    public DigitalTouristPass() {}

    public DigitalTouristPass(Long touristId, String passNumber, String passType, LocalDate issueDate, LocalDate expiryDate, Double amount, String paymentStatus, String status) {
        this.touristId = touristId;
        this.passNumber = passNumber;
        this.passType = passType;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTouristId() { return touristId; }
    public void setTouristId(Long touristId) { this.touristId = touristId; }

    public String getPassNumber() { return passNumber; }
    public void setPassNumber(String passNumber) { this.passNumber = passNumber; }

    public String getPassType() { return passType; }
    public void setPassType(String passType) { this.passType = passType; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
