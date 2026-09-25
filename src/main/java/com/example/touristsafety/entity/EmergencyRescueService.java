package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emergency_rescue_services")
public class EmergencyRescueService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String serviceProvider;
    private String contactNumber;
    private String serviceArea;
    private Double cost;
    private String availabilityStatus; // AVAILABLE, BUSY, MAINTENANCE

    public EmergencyRescueService() {}

    public EmergencyRescueService(String serviceName, String serviceProvider, String contactNumber, String serviceArea, Double cost, String availabilityStatus) {
        this.serviceName = serviceName;
        this.serviceProvider = serviceProvider;
        this.contactNumber = contactNumber;
        this.serviceArea = serviceArea;
        this.cost = cost;
        this.availabilityStatus = availabilityStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getServiceProvider() { return serviceProvider; }
    public void setServiceProvider(String serviceProvider) { this.serviceProvider = serviceProvider; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getServiceArea() { return serviceArea; }
    public void setServiceArea(String serviceArea) { this.serviceArea = serviceArea; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}
