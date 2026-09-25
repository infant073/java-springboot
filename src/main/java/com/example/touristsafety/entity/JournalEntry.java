package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "journal_entries")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long journalId;
    private String accountName;
    private Double debit;
    private Double credit;
    private String description;

    public JournalEntry() {}

    public JournalEntry(Long journalId, String accountName, Double debit, Double credit, String description) {
        this.journalId = journalId;
        this.accountName = accountName;
        this.debit = debit != null ? debit : 0.0;
        this.credit = credit != null ? credit : 0.0;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJournalId() { return journalId; }
    public void setJournalId(Long journalId) { this.journalId = journalId; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public Double getDebit() { return debit; }
    public void setDebit(Double debit) { this.debit = debit; }

    public Double getCredit() { return credit; }
    public void setCredit(Double credit) { this.credit = credit; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
