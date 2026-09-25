package com.example.touristsafety.service;

import com.example.touristsafety.dto.GeoCheckResultDto;
import com.example.touristsafety.entity.SafetyZone;
import com.example.touristsafety.repository.SafetyZoneRepository;
import com.example.touristsafety.util.GeoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoFencingService {

    @Autowired
    private SafetyZoneRepository safetyZoneRepository;

    public GeoCheckResultDto checkCoordinates(double latitude, double longitude) {
        List<SafetyZone> zones = safetyZoneRepository.findAll();
        
        SafetyZone matchedZone = null;
        double minDistance = Double.MAX_VALUE;

        for (SafetyZone zone : zones) {
            if ("INACTIVE".equalsIgnoreCase(zone.getStatus())) {
                continue;
            }
            double dist = GeoUtils.calculateDistanceKm(latitude, longitude, zone.getLatitude(), zone.getLongitude());
            if (dist <= zone.getRadius()) {
                if (dist < minDistance) {
                    minDistance = dist;
                    matchedZone = zone;
                }
            }
        }

        if (matchedZone != null) {
            return new GeoCheckResultDto(
                    true,
                    matchedZone,
                    Math.round(minDistance * 100.0) / 100.0,
                    matchedZone.getWarningMessage() != null ? matchedZone.getWarningMessage() : "DANGER ZONE WARNING: You have entered " + matchedZone.getZoneName()
            );
        } else {
            // Find nearest zone for informational purposes
            SafetyZone nearestZone = null;
            double nearestDist = Double.MAX_VALUE;
            for (SafetyZone zone : zones) {
                double dist = GeoUtils.calculateDistanceKm(latitude, longitude, zone.getLatitude(), zone.getLongitude());
                if (dist < nearestDist) {
                    nearestDist = dist;
                    nearestZone = zone;
                }
            }

            return new GeoCheckResultDto(
                    false,
                    nearestZone,
                    nearestZone != null ? Math.round(nearestDist * 100.0) / 100.0 : null,
                    "You are in a safe area."
            );
        }
    }
}
