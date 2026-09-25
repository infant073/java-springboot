package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vendor_bills")
public class VendorBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billNumber;
    private Long purchaseOrderId;
    private Long vendorId;
    private LocalDate billDate;
    private Double amount;
    private String paymentStatus; // UNPAID, PARTIAL, PAID

    public VendorBill() {}

    public VendorBill(String billNumber, Long purchaseOrderId, Long vendorId, LocalDate billDate, Double amount, String paymentStatus) {
        this.billNumber = billNumber;
        this.purchaseOrderId = purchaseOrderId;
        this.vendorId = vendorId;
        this.billDate = billDate;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }

    public Long getPurchaseOrderId() { return purchaseOrderId; }
    public void setPurchaseOrderId(Long purchaseOrderId) { this.purchaseOrderId = purchaseOrderId; }

    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }

    public LocalDate getBillDate() { return billDate; }
    public void setBillDate(LocalDate billDate) { this.billDate = billDate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
