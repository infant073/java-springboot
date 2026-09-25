package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String poNumber;
    private Long vendorId;
    private LocalDate orderDate;
    private String description;
    private Double totalAmount;
    private String status; // DRAFT, APPROVED, BILLED, PAID, CANCELLED

    public PurchaseOrder() {}

    public PurchaseOrder(String poNumber, Long vendorId, LocalDate orderDate, String description, Double totalAmount, String status) {
        this.poNumber = poNumber;
        this.vendorId = vendorId;
        this.orderDate = orderDate;
        this.description = description;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPoNumber() { return poNumber; }
    public void setPoNumber(String poNumber) { this.poNumber = poNumber; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
