package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "analytical_accounts")
public class AnalyticalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private String accountType; // REVENUE, EXPENSE, ASSET, LIABILITY
    private String description;
    private String zone;

    public AnalyticalAccount() {}

    public AnalyticalAccount(String accountName, String accountType, String description, String zone) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.description = description;
        this.zone = zone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
}
