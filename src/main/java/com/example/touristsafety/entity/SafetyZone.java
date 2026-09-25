package com.example.touristsafety.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "safety_zones")
public class SafetyZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;
    @Column(length = 1000)
    private String description;
    private Double latitude;
    private Double longitude;
    private Double radius; // Radius in KM
    private String dangerLevel; // LOW, MEDIUM, HIGH, CRITICAL
    private String warningMessage;
    private String status; // ACTIVE, INACTIVE

    public SafetyZone() {}

    public SafetyZone(String zoneName, String description, Double latitude, Double longitude, Double radius, String dangerLevel, String warningMessage, String status) {
        this.zoneName = zoneName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.dangerLevel = dangerLevel;
        this.warningMessage = warningMessage;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getRadius() { return radius; }
    public void setRadius(Double radius) { this.radius = radius; }

    public String getDangerLevel() { return dangerLevel; }
    public void setDangerLevel(String dangerLevel) { this.dangerLevel = dangerLevel; }

    public String getWarningMessage() { return warningMessage; }
    public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
