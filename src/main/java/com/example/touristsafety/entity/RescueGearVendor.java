package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rescue_gear_vendors")
public class RescueGearVendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vendorName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String gstNumber;
    private String status; // ACTIVE, INACTIVE

    public RescueGearVendor() {}

    public RescueGearVendor(String vendorName, String contactPerson, String email, String phone, String address, String gstNumber, String status) {
        this.vendorName = vendorName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.gstNumber = gstNumber;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
