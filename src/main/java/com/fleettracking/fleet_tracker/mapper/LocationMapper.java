package com.fleettracking.fleet_tracker.mapper;

import org.springframework.stereotype.Component;

import com.fleettracking.fleet_tracker.dto.request.LocationRequestDto;
import com.fleettracking.fleet_tracker.dto.response.LocationResponseDto;
import com.fleettracking.fleet_tracker.entity.Device;
import com.fleettracking.fleet_tracker.entity.Location;

@Component
public class LocationMapper {

	public Location toEntity(LocationRequestDto dto, Device device) {
		return Location.builder().device(device).latitude(dto.getLatitude()).longitude(dto.getLongitude())
				.speed(dto.getSpeed()).heading(dto.getHeading()).altitude(dto.getAltitude()).build();
	}

	public LocationResponseDto toResponseDto(Location location) {
		LocationResponseDto dto = new LocationResponseDto();
		dto.setId(location.getId());
		dto.setDeviceId(location.getDevice().getId());
		dto.setDeviceCode(location.getDevice().getDeviceCode());
		dto.setLatitude(location.getLatitude());
		dto.setLongitude(location.getLongitude());
		dto.setSpeed(location.getSpeed());
		dto.setHeading(location.getHeading());
		dto.setRecordedAt(location.getRecordedAt());
		return dto;
	}

}
