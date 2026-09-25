package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceNumber;
    private String transactionType; // VENDOR_PAYMENT, TOURIST_PASS_PAYMENT
    private Long transactionId;
    private LocalDate paymentDate;
    private Double amount;
    private String paymentMethod; // CASH, DIGITAL, BANK_TRANSFER, CREDIT_CARD
    private String status; // COMPLETED, PENDING, FAILED

    public Payment() {}

    public Payment(String referenceNumber, String transactionType, Long transactionId, LocalDate paymentDate, Double amount, String paymentMethod, String status) {
        this.referenceNumber = referenceNumber;
        this.transactionType = transactionType;
        this.transactionId = transactionId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
