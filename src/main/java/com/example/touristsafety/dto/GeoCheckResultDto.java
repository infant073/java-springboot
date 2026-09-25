package com.example.touristsafety.dto;

import com.example.touristsafety.entity.SafetyZone;

public class GeoCheckResultDto {

    private boolean insideDangerZone;
    private SafetyZone zone;
    private Double distanceKm;
    private String warningMessage;

    public GeoCheckResultDto() {}

    public GeoCheckResultDto(boolean insideDangerZone, SafetyZone zone, Double distanceKm, String warningMessage) {
        this.insideDangerZone = insideDangerZone;
        this.zone = zone;
        this.distanceKm = distanceKm;
        this.warningMessage = warningMessage;
    }

    public boolean isInsideDangerZone() { return insideDangerZone; }
    public void setInsideDangerZone(boolean insideDangerZone) { this.insideDangerZone = insideDangerZone; }

    public SafetyZone getZone() { return zone; }
    public void setZone(SafetyZone zone) { this.zone = zone; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public String getWarningMessage() { return warningMessage; }
    public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }
}
