package com.fleettracking.fleet_tracker.service.location;

import java.util.List;

import com.fleettracking.fleet_tracker.dto.request.LocationRequestDto;
import com.fleettracking.fleet_tracker.dto.response.LocationResponseDto;
import com.fleettracking.fleet_tracker.dto.response.NearbyDeviceResponseDto;

public interface LocationService {

	LocationResponseDto saveLocation(LocationRequestDto dto);

	LocationResponseDto getLastLocation(Long deviceId);

	List<LocationResponseDto> getLocationHistory(Long deviceId);

	List<NearbyDeviceResponseDto> findNearbyDevices(double lat, double lon, double radiusKm);
}
