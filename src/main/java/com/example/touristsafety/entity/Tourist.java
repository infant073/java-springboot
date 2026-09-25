package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tourists")
public class Tourist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String digitalPassNumber;
    private String passType;
    private LocalDate passStartDate;
    private LocalDate passEndDate;
    private String status; // ACTIVE, INACTIVE, EXPIRED

    public Tourist() {}

    public Tourist(String name, String email, String phone, String address, String emergencyContactName, String emergencyContactPhone, String digitalPassNumber, String passType, LocalDate passStartDate, LocalDate passEndDate, String status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.digitalPassNumber = digitalPassNumber;
        this.passType = passType;
        this.passStartDate = passStartDate;
        this.passEndDate = passEndDate;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }

    public String getDigitalPassNumber() { return digitalPassNumber; }
    public void setDigitalPassNumber(String digitalPassNumber) { this.digitalPassNumber = digitalPassNumber; }

    public String getPassType() { return passType; }
    public void setPassType(String passType) { this.passType = passType; }

    public LocalDate getPassStartDate() { return passStartDate; }
    public void setPassStartDate(LocalDate passStartDate) { this.passStartDate = passStartDate; }

    public LocalDate getPassEndDate() { return passEndDate; }
    public void setPassEndDate(LocalDate passEndDate) { this.passEndDate = passEndDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
