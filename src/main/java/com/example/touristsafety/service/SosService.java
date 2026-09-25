package com.example.touristsafety.service;

import com.example.touristsafety.dto.GeoCheckResultDto;
import com.example.touristsafety.dto.SosRequestDto;
import com.example.touristsafety.entity.EmergencyResponseOperation;
import com.example.touristsafety.entity.SosAlert;
import com.example.touristsafety.entity.Tourist;
import com.example.touristsafety.exception.ResourceNotFoundException;
import com.example.touristsafety.repository.EmergencyResponseOperationRepository;
import com.example.touristsafety.repository.SosAlertRepository;
import com.example.touristsafety.repository.TouristRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SosService {

    @Autowired
    private SosAlertRepository sosAlertRepository;

    @Autowired
    private TouristRepository touristRepository;

    @Autowired
    private EmergencyResponseOperationRepository responseOperationRepository;

    @Autowired
    private GeoFencingService geoFencingService;

    @Transactional
    public SosAlert triggerSos(Long touristId, SosRequestDto sosRequest) {
        Tourist tourist = touristRepository.findById(touristId)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist not found with id: " + touristId));

        SosAlert alert = new SosAlert();
        alert.setTouristId(tourist.getId());
        alert.setTouristName(tourist.getName());
        alert.setLatitude(sosRequest.getLatitude());
        alert.setLongitude(sosRequest.getLongitude());
        alert.setAlertTime(LocalDateTime.now());
        alert.setAlertType(sosRequest.getAlertType() != null ? sosRequest.getAlertType() : "GENERAL_EMERGENCY");
        alert.setMessage(sosRequest.getMessage() != null ? sosRequest.getMessage() : "Emergency assistance requested");
        alert.setStatus("ACTIVE");
        alert.setResponseStatus("DISPATCHED");

        // Geo-fencing evaluation
        GeoCheckResultDto geoCheck = geoFencingService.checkCoordinates(sosRequest.getLatitude(), sosRequest.getLongitude());
        if (geoCheck.isInsideDangerZone() && geoCheck.getZone() != null) {
            alert.setInsideZoneId(geoCheck.getZone().getId());
            alert.setInsideZoneName(geoCheck.getZone().getZoneName());
            alert.setDistanceToZoneKm(geoCheck.getDistanceKm());
        } else if (geoCheck.getZone() != null) {
            alert.setDistanceToZoneKm(geoCheck.getDistanceKm());
        }

        SosAlert savedAlert = sosAlertRepository.save(alert);

        // Auto-create initial Emergency Response Operation
        EmergencyResponseOperation operation = new EmergencyResponseOperation(
                savedAlert.getId(),
                "Rapid Response Team Alpha",
                LocalDateTime.now(),
                "Lat: " + sosRequest.getLatitude() + ", Lng: " + sosRequest.getLongitude(),
                "Automatic emergency dispatch triggered for tourist " + tourist.getName() + " (" + alert.getAlertType() + ")",
                150.0, // initial estimated fuel cost
                300.0, // initial estimated equipment cost
                500.0, // initial estimated service cost
                "DISPATCHED"
        );
        responseOperationRepository.save(operation);

        return savedAlert;
    }

    public SosAlert updateStatus(Long sosId, String status) {
        SosAlert alert = sosAlertRepository.findById(sosId)
                .orElseThrow(() -> new ResourceNotFoundException("SOS Alert not found with id: " + sosId));
        alert.setStatus(status.toUpperCase());
        alert.setResponseStatus(status.toUpperCase());
        return sosAlertRepository.save(alert);
    }
}
