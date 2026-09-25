package com.example.touristsafety.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sos_alerts")
public class SosAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long touristId;
    private String touristName;
    private Double latitude;
    private Double longitude;
    private LocalDateTime alertTime;
    private String alertType; // MEDICAL, LOST, ACCIDENT, ANIMAL_ATTACK, WEATHER
    private String message;
    private String status; // ACTIVE, DISPATCHED, IN_PROGRESS, RESOLVED, CANCELLED
    private String responseStatus;
    
    private Long insideZoneId;
    private String insideZoneName;
    private Double distanceToZoneKm;

    public SosAlert() {}

    public SosAlert(Long touristId, String touristName, Double latitude, Double longitude, LocalDateTime alertTime, String alertType, String message, String status, String responseStatus) {
        this.touristId = touristId;
        this.touristName = touristName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alertTime = alertTime;
        this.alertType = alertType;
        this.message = message;
        this.status = status;
        this.responseStatus = responseStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTouristId() { return touristId; }
    public void setTouristId(Long touristId) { this.touristId = touristId; }

    public String getTouristName() { return touristName; }
    public void setTouristName(String touristName) { this.touristName = touristName; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getAlertTime() { return alertTime; }
    public void setAlertTime(LocalDateTime alertTime) { this.alertTime = alertTime; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResponseStatus() { return responseStatus; }
    public void setResponseStatus(String responseStatus) { this.responseStatus = responseStatus; }

    public Long getInsideZoneId() { return insideZoneId; }
    public void setInsideZoneId(Long insideZoneId) { this.insideZoneId = insideZoneId; }

    public String getInsideZoneName() { return insideZoneName; }
    public void setInsideZoneName(String insideZoneName) { this.insideZoneName = insideZoneName; }

    public Double getDistanceToZoneKm() { return distanceToZoneKm; }
    public void setDistanceToZoneKm(Double distanceToZoneKm) { this.distanceToZoneKm = distanceToZoneKm; }
}
