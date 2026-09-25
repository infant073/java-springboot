package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "telecom_providers")
public class TelecomProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;
    private String contactPerson;
    private String phone;
    private String email;
    private String serviceType; // SATELLITE, MESH_RADIO, 5G_CELLULAR
    private String status; // ACTIVE, INACTIVE

    public TelecomProvider() {}

    public TelecomProvider(String providerName, String contactPerson, String phone, String email, String serviceType, String status) {
        this.providerName = providerName;
        this.contactPerson = contactPerson;
        this.phone = phone;
        this.email = email;
        this.serviceType = serviceType;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
