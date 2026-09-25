package com.example.touristsafety.dto;

import jakarta.validation.constraints.NotNull;

public class SosRequestDto {

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    private String alertType; // MEDICAL, LOST, ACCIDENT, ANIMAL_ATTACK, WEATHER
    private String message;

    public SosRequestDto() {}

    public SosRequestDto(Double latitude, Double longitude, String alertType, String message) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.alertType = alertType;
        this.message = message;
    }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
