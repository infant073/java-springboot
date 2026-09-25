package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "journals")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String journalNumber;
    private LocalDate journalDate;
    private String description;
    private String referenceType; // TOURIST_PASS, VENDOR_BILL, PAYMENT, RESCUE_OPERATION
    private Long referenceId;

    public Journal() {}

    public Journal(String journalNumber, LocalDate journalDate, String description, String referenceType, Long referenceId) {
        this.journalNumber = journalNumber;
        this.journalDate = journalDate;
        this.description = description;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getJournalNumber() { return journalNumber; }
    public void setJournalNumber(String journalNumber) { this.journalNumber = journalNumber; }

    public LocalDate getJournalDate() { return journalDate; }
    public void setJournalDate(LocalDate journalDate) { this.journalDate = journalDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReferenceType() { return referenceType; }
    public void setReferenceType(String referenceType) { this.referenceType = referenceType; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }
}
