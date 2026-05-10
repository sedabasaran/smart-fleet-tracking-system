package com.fleettracking.fleet_tracker.service.location;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fleettracking.fleet_tracker.dto.request.LocationRequestDto;
import com.fleettracking.fleet_tracker.dto.response.LocationResponseDto;
import com.fleettracking.fleet_tracker.dto.response.NearbyDeviceResponseDto;
import com.fleettracking.fleet_tracker.entity.Device;
import com.fleettracking.fleet_tracker.entity.Location;
import com.fleettracking.fleet_tracker.event.LocationUpdatedEvent;
import com.fleettracking.fleet_tracker.exception.DeviceNotFoundException;
import com.fleettracking.fleet_tracker.mapper.LocationMapper;
import com.fleettracking.fleet_tracker.repository.DeviceRepository;
import com.fleettracking.fleet_tracker.repository.LocationRepository;
import com.fleettracking.fleet_tracker.service.geo.GeoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

	private final LocationRepository locationRepository;
	private final DeviceRepository deviceRepository;
	private final LocationMapper locationMapper;
	private final GeoService geoService;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public LocationResponseDto saveLocation(LocationRequestDto dto) {
		Device device = deviceRepository.findById(dto.getDeviceId())
				.orElseThrow(() -> new DeviceNotFoundException(dto.getDeviceId()));

		Location location = locationMapper.toEntity(dto, device);
		Location saved = locationRepository.save(location);

		eventPublisher.publishEvent(new LocationUpdatedEvent(this, saved));

		return locationMapper.toResponseDto(saved);
	}

	@Override
	public LocationResponseDto getLastLocation(Long deviceId) {
		return locationRepository.findTopByDeviceIdOrderByRecordedAtDesc(deviceId).map(locationMapper::toResponseDto)
				.orElseThrow(() -> new DeviceNotFoundException(deviceId));
	}

	@Override
	public List<LocationResponseDto> getLocationHistory(Long deviceId) {
		return locationRepository.findByDeviceIdOrderByRecordedAtDesc(deviceId).stream()
				.map(locationMapper::toResponseDto).collect(Collectors.toList());
	}

	@Override
	public List<NearbyDeviceResponseDto> findNearbyDevices(double lat, double lon, double radiusKm) {
		return locationRepository.findLocationsWithinRadius(lat, lon, radiusKm).stream().map(loc -> {
			NearbyDeviceResponseDto dto = new NearbyDeviceResponseDto();
			dto.setDeviceId(loc.getDevice().getId());
			dto.setDeviceCode(loc.getDevice().getDeviceCode());
			dto.setName(loc.getDevice().getName());
			dto.setLatitude(loc.getLatitude());
			dto.setLongitude(loc.getLongitude());
			dto.setDistanceKm(geoService.calculateDistance(lat, lon, loc.getLatitude(), loc.getLongitude()));
			return dto;
		}).collect(Collectors.toList());
	}

}
