package com.fleettracking.fleet_tracker.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fleettracking.fleet_tracker.common.ApiResponse;
import com.fleettracking.fleet_tracker.dto.request.LocationRequestDto;
import com.fleettracking.fleet_tracker.dto.response.LocationResponseDto;
import com.fleettracking.fleet_tracker.dto.response.NearbyDeviceResponseDto;
import com.fleettracking.fleet_tracker.service.location.LocationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {
	
	private final LocationService locationService;

    @PostMapping
    public ResponseEntity<ApiResponse<LocationResponseDto>> save(
            @Valid @RequestBody LocationRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(locationService.saveLocation(dto)));
    }

    @GetMapping("/device/{deviceId}/last")
    public ResponseEntity<ApiResponse<LocationResponseDto>> getLast(@PathVariable Long deviceId) {
        return ResponseEntity.ok(ApiResponse.success(locationService.getLastLocation(deviceId)));
    }

    @GetMapping("/device/{deviceId}/history")
    public ResponseEntity<ApiResponse<List<LocationResponseDto>>> getHistory(
            @PathVariable Long deviceId) {
        return ResponseEntity.ok(ApiResponse.success(locationService.getLocationHistory(deviceId)));
    }

    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<NearbyDeviceResponseDto>>> getNearby(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5.0") double radiusKm) {
        return ResponseEntity.ok(
            ApiResponse.success(locationService.findNearbyDevices(lat, lon, radiusKm))
        );
    }

}
